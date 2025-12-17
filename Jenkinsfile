pipeline {
    agent any  // Runs inside your Jenkins container
    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from GitHub
                git branch: 'main', url: 'https://github.com/Ankurbhardwaj25/loggingdemo.git'
            }
        }

        stage('Build JAR') {
            steps {
                // Always run inside workspace
                dir("${env.WORKSPACE}") {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir("${env.WORKSPACE}") {
                    // Build Docker image using host Docker
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Run Container') {
            steps {
                dir("${env.WORKSPACE}") {
                    // Stop existing container if running
                    sh "docker rm -f ${IMAGE_NAME} || true"

                    // Make sure port is free
                    sh "fuser -k ${CONTAINER_PORT}/tcp || true"

                    // Run new container
                    sh "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"

                    // Show running containers
                    sh "docker ps -f name=${IMAGE_NAME}"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
    }
}
