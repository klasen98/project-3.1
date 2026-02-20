pipeline {
    agent any
    tools {
        jdk 'Java-24'
    }
    stages {
        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw clean package -Dspring.profiles.active=H2'
            }
        }
    }
}