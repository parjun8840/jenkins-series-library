def call(String repoUrl) {
pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: maven
            image: maven:alpine
            command:
            - cat
            tty: true
        '''
    }
  }
  stages {
    stage("Checkout Code") {
               steps {
                   git branch: 'main',
                       url: "${repoUrl}"
               }
           }
    stage("Cleaning workspace") {
               steps {
                   echo "Cleaning workspace"
                   sh "mvn -version"
               }
           }
    stage("Running Testcase") {
              steps {
                   echo "Running Testcase"
                   sh "mvn -version"
               }
          }
    stage("Packing Application") {
               steps {
                   echo "Packing Application"
                   sh "mvn -version"
               }
           }
    stage("Build Docker"){
               steps {
                   echo "Build Docker"
                   sh "mvn -version"
               }
           }
  }
}
}
