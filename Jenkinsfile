pipeline {
    agent any
    environment {
        IMAGE_NAME = "loggingdemo-springboot"
        IMAGE_TAG = "1.0"
        CONTAINER_PORT = "8081"
    }
    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }
        stage('Checkout') {
            steps {
                // Correct Git checkout that works in empty workspace
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: 'main']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Ankurbhardwaj25/loggingdemo.git'
                    ]]
                ])
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
    post {
        always {
            echo 'Pipeline finished'
        }
    }
}
