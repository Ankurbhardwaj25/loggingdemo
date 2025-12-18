pipeline {
    agent any

    tools {
        // This name MUST match what you saved in Manage Jenkins > Tools
        maven 'maven3'
    }

    options {
        // Helps avoid the initial Git 128 error
        skipDefaultCheckout()
    }

    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
        REPO_URL = "https://github.com/Ankurbhardwaj25/loggingdemo.git"
    }

    stages {
        stage('Clean & Initialize') {
            steps {
                deleteDir()
                // Fixes "dubious ownership" errors inside Docker containers
                sh "git config --global --add safe.directory '*'"
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build JAR') {
            steps {
                // Now 'mvn' will be found because of the 'tools' block
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Deploy') {
            steps {
                sh "docker rm -f ${IMAGE_NAME} || true"
                sh "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }

    post {
        always {
            sh 'docker image prune -f'
            echo 'Build Process Completed'
        }
    }
}