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
                    sh 'ls -lrt'
                      }
           }
    stage('Docker login') {
         steps {
            script{
            dockerImage = docker.build("$DOCKER_USR/prometheus:${env.BUILD_TAG}") 
            }
             }
            }
    stage('Publish') {
    steps {
      echo 'Building and publishing multi-arch image to DockerHub..'
      script{
      withDockerRegistry([ credentialsId: "DOCKER_ID", url: "" ]) {
        dockerImage.push()
        }
}
}
}
}
}
}
