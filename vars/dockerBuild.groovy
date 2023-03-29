def call(String repoUrl) {
pipeline {
  agent {label 'DONT-USE-THIS'}
  environment {
    DOCKER = credentials('DOCKER_ID')
              }
  stages {
    stage("Checkout Code") {
               steps {
                   git branch: 'main',
                       url: "${repoUrl}"
                      }
                   sh 'ls -lrt'
           }
    stage('Docker login') {
         steps { 
                echo 'Docker login'
                sh 'echo $DOCKER_PSW | docker login -u $DOCKER_USR --password-stdin'
            }
            }
    stage('Publish') {
    steps {
      echo 'Building and publishing multi-arch image to DockerHub..'
      sh 'docker buildx build --push --platform linux/amd64,linux/arm64 -t $DOCKER_ID/prometheus:latest .'
}
}
}
}
}
