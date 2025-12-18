pipeline {
    agent any

    options {
        // Disables the automatic checkout so we can clean the workspace first
        skipDefaultCheckout()
    }

    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
        // Reference to your git repo to keep the checkout step clean
        REPO_URL = "https://github.com/Ankurbhardwaj25/loggingdemo.git"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                // Safely wipes the directory without breaking Git tracking
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                // Explicit checkout after the workspace is fresh
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build JAR') {
            steps {
                // Using the Maven wrapper if available, otherwise 'mvn'
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
                // 1. Force remove existing container if it exists
                sh "docker rm -f ${IMAGE_NAME} || true"
                // 2. Clear the port if it's being held by a zombie process
                sh "fuser -k ${CONTAINER_PORT}/tcp || true"
                // 3. Start the new container
                sh "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }

    post {
        success {
            echo "Deployment successful: http://localhost:${CONTAINER_PORT}"
        }
        always {
            // Clean up "dangling" docker images to save disk space
            sh 'docker image prune -f'
            echo 'Pipeline finished'
        }
        failure {
            echo 'Pipeline failed. Check the console output above for Maven or Docker errors.'
        }
    }
}