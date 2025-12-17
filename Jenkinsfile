pipeline {
    agent any  // Runs directly inside Jenkins container
    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
    }
    stages {
        stage('Checkout') {
            steps { git branch: 'main', url: 'https://github.com/Ankurbhardwaj25/loggingdemo.git' }
        }
       stage('Build JAR') {
           steps {
               dir("${env.WORKSPACE}") {
                   sh 'mvn clean package -DskipTests'
               }
           }
       }

      stage('Build Docker Image') {
          steps {
              dir("${env.WORKSPACE}") {
                  sh "docker build -t loggingdemo-springboot:1.0 ."
              }
          }
      }

        stage('Run Container') {
            steps {
                sh "docker rm -f ${IMAGE_NAME} || true"
                sh "docker run -d -p 8081:8081 --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }
}
