def call() {
    pipeline {

        agent {
            node {
                label 'workstation-project'
            }
        }

        environment {
            NEXUS = credentials('NEXUS')
        }


        stages {
            stage('Code Quality') {
                steps {

                    sh 'echo Code Quality'
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.9.183:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                }
            }
            stage('Unit testng') {
                steps {

                    sh 'echo Unit Testing'
                }
            }
            stage('Checkmarx SAST Scan') {
                steps {

                    sh 'echo SAST Scan'
                }
            }
            stage('Checkmarx SCA Scan') {
                steps {

                    sh 'echo SCA Scan'
                }
            }

            stage('Release Application') {
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'echo $TAG_NAME >VERSION'
                    sh 'zip -r frontend-${TAG_NAME}.zip *'
                    // Deleting this file as it is not needed.
                    sh 'zip -d frontend-${TAG_NAME}.zip Jenkinsfile'
                    sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file frontend-${TAG_NAME}.zip http://172.31.14.209:8081/repository/frontend/frontend-${TAG_NAME}.zip'
                }
            }
        }
            post {
                always {
                    cleanWs()
                }
            }

        }
    }
