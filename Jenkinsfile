pipeline {
    agent any

    // This tells Jenkins to handle the Maven installation for you
    tools {
        maven 'maven3'
    }

    options {
        // Prevents the "not in a git directory" error by avoiding implicit checkout
        skipDefaultCheckout()
    }

    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
        REPO_URL = "https://github.com/Ankurbhardwaj25/loggingdemo.git"
    }

    stages {
        stage('Initialize') {
            steps {
                // Ensure workspace is clean but git-ready
                cleanWs()
                sh "git config --global --add safe.directory '*'"
            }
        }

        stage('Checkout') {
            steps {
                // Explicitly pull the code
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Build JAR') {
            steps {
                // Because of the 'tools' block, 'mvn' is now in your PATH
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // This works because you mounted /var/run/docker.sock
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Deploy Container') {
            steps {
                // Stop old container and start new one on the host machine
                sh "docker rm -f ${IMAGE_NAME} || true"
                sh "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${IMAGE_NAME} ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished. Cleaning up dangling images...'
            sh 'docker image prune -f'
        }
    }
}