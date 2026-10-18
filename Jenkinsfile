pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        CI = 'true'
    }

    stages {
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
        }
    }
}