pipeline {
    agent any
    options { skipDefaultCheckout() }
    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
        REPO_URL = "https://github.com/Ankurbhardwaj25/loggingdemo.git"
    }
    stages {
        stage('Initialize & Debug') {
            steps {
                deleteDir()
                sh "git config --global --add safe.directory '*'"
                sh "git init"
                script {
                    echo "Checking environment..."
                    sh "whoami && pwd"
                    sh "ls -la"
                }
            }
        }
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
            }
        }
        stage('Build JAR') {
            steps { sh 'mvn clean package -DskipTests' }
        }
        stage('Build Docker Image') {
            steps { sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ." }
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