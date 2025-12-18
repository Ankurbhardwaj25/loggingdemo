pipeline {
    agent any

    options {
        // 1. Crucial: Prevents Jenkins from trying to be "smart" with Git at the start
        skipDefaultCheckout()
        // 2. Ensures the workspace is totally fresh at the start of every build
        skipStagesAfterUnstable()
    }

    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
        REPO_URL = "https://github.com/Ankurbhardwaj25/loggingdemo.git"
    }

    stages {
        stage('Initialize Workspace') {
            steps {
                // Delete everything first
                deleteDir()
                // Explicitly initialize the directory so Git doesn't complain
                sh 'git init'
            }
        }

        stage('Checkout') {
            steps {
                // Now we perform the actual checkout
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Run Container') {
            steps {
                sh "docker rm -f ${IMAGE_NAME} || true"
                sh "fuser -k ${CONTAINER_PORT}/tcp || true"
                sh "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }
}