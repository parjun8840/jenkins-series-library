def call(String repoUrl) {
pipeline {
  environment {
    DOCKER = credentials('DOCKER_ID')
              }
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: docker-build
            image: moby/buildkit:latest
            command:
            - cat
            tty: true
        '''
    }
  }
  stages {
    stage("Checkout Code") {
               steps {
                   container('docker-build') {
                   git branch: 'main',
                       url: "${repoUrl}"
                   sh 'ls -lrt'
                      }
               }
           }
    stage('Docker login') {
         steps {  container('docker-build') {
                echo 'Docker login'
                //sh 'echo $DOCKER_PSW | docker login -u $DOCKER_USR --password-stdin'
                sh "buildctl build --frontend dockerfile.v0 --local context=. --local dockerfile=. --output type=image,name=${DOCKER_USR}:${env.BUILD_TAG},push=false"
            }
        }

                }
            
    stage('Publish') {
    steps {  container('docker-build') {
    echo 'Building and publishing multi-arch image to DockerHub..'
//sh "docker buildx build --push --platform linux/amd64,linux/arm64 -t $DOCKER_USR/prometheus:${env.BUILD_TAG} ."
 // sh "docker build  -t $DOCKER_USR/prometheus:${env.BUILD_TAG} ." 
}
}
}
}
}
}
