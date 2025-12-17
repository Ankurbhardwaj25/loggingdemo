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
                sh 'apt update && apt install -y maven openjdk-17-jdk && mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps { sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ." }
        }
        stage('Run Container') {
            steps {
                sh "docker rm -f ${IMAGE_NAME} || true"
                sh "docker run -d -p 8081:8081 --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }
}
