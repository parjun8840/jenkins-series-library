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
                   container('maven') {
                   git branch: 'main',
                       url: "${repoUrl}"
                      }
               }
           }
    stage("Cleaning workspace") {
               steps {
                   container('maven'){
                   echo "Cleaning workspace"
                   sh "mvn -version"
                   sh "ls -lrt"
                  }
               }
           }
    stage("Running Testcase") {
              steps {
                   container('maven'){
                   echo "Running Testcase"
                   sh "mvn -version"
                   }
               }
          }
    stage("Packing Application") {
               steps {
                   container('maven'){
                   echo "Packing Application"
                   sh "mvn -version"
                  }
               }
           }
    stage("Build Docker"){
               steps {
                   container('maven') {
                   echo "Build Docker"
                   sh "mvn -version"
                }
               }
           }
  }
}
}
