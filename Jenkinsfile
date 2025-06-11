pipeline {
    agent any

    tools {
        maven 'Maven'
    }
    
     environment {
        EMAIL_RECIPIENTS = 'marutih245@gmail.com'  // Change to your actual recipients
    }
    
    stages {
        stage("Checkout from SCM") {
            steps {
                git branch: 'main', url: 'https://github.com/marutih8/M-CT-case1.git'
            }
        }
    

        stage("Build Application") {
            steps {
                sh 'mvn clean install'
            }
        }

        stage("SonarQube Analysis") {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
         }
             
             stage("Quality Gate") {
                steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar'
                }
            }
         }
         
           stage("Build, Scan and Push Docker Image") {
              steps {
                script {
                    // Replace 'docker-credentials-id' with your Jenkins Docker credentials ID
                    withDockerRegistry(credentialsId: 'dockerID') {
                        sh 'docker build -t my-app-name:latest -f Dockerfile .'
                        sh 'trivy image --severity CRITICAL --exit-code 0 my-app-name:latest'
                        sh 'docker tag my-app-name:latest marutih8/my-app-name'
                        sh 'docker push marutih8/my-app-name'
                    }
                }

            }
        }
          
              stage('Deploy to Kubernetes') {
            steps {
              withCredentials([usernamePassword(credentialsId: 'aws-credentials', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
              withCredentials([file(credentialsId: 'kubeID', variable: 'KUBECONFIG')]) {
               sh 'kubectl apply -f deployment.yaml'
               sh 'kubectl apply -f service.yaml'
                   
                 }
               }
            }
        }
        
        stage('Deploy to dev') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'aws-credentials', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    withEnv(["AWS_DEFAULT_REGION=ap-south-1"]) {
                        sh '''
                            aws eks update-kubeconfig --name my-cluster --region ap-south-1
                            kubectl apply -f deployment.yaml --namespace=dev
                            kubectl apply -f service.yaml --namespace=dev
                        '''
                    }
                }
            }
        }
    
        
        // stage('Deploy to qa') {
        //     steps {
        //         withCredentials([usernamePassword(credentialsId: 'aws-credentials', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
        //             withEnv(["AWS_DEFAULT_REGION=ap-south-1"]) {
        //                 sh '''
        //                     aws eks update-kubeconfig --name my-cluster --region ap-south-1
        //                     kubectl apply -f deployment.yaml --namespace=qa
        //                     kubectl apply -f service.yaml --namespace=qa
        //                 '''
        //             }
        //         }
        //       }
        //  }
        
//         stage('Approval to Deploy to Prod') {
//             steps {
//                 script {
//                     input message: 'Approve deployment to Production?', parameters: [
//                         booleanParam(name: 'Proceed', defaultValue: false, description: 'Do you want to deploy to production?')
//                     ]
//                 }
//             }
//         }

//         stage('Deploy to Prod') {
//             when {
//                 expression { params.Proceed == true }
//             }
//             steps {
//                 echo 'Deploying to Production...'
//                 // your deploy logic here
//             }
//         }
//     }

//     post {
//         failure {
//             script {
//                 emailext(
//                     subject: "Jenkins Pipeline Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
//                     body: """<p>Build failed for job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
//                              <p>Check console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
//                     to: "${env.EMAIL_RECIPIENTS}",
//                     mimeType: 'text/html'
//                 )
//             }
//         }
//      }
   } 
}
