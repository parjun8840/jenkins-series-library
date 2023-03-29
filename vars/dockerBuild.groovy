def call(String repoUrl) {
pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: docker-build
            image: docker/buildx-bin:latest
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
                      }
                   sh 'ls -lrt'
               }
           }
/*
    stage('Docker login') {
         steps {  container('docker-build') {
                echo 'Docker login'
                sh 'echo $DOCKER_PSW | docker login -u $DOCKER_USR --password-stdin'
                }
            }
            }
    stage('Publish') {
    steps {  container('docker-build') {
    echo 'Building and publishing multi-arch image to DockerHub..'
sh 'docker buildx build --push --platform linux/amd64,linux/arm64 -t $DOCKER_ID/prometheus:latest .'
}
}
}
*/
}
}
}
