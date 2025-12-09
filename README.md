# ToDo Application: Spring Boot, React, and MySQL with Docker Compose

## check Check the Docker Status in linux environment  
   ```sudo systemctl status docker```  
   Expected Output: You should see a status indicating ```active (running)```.  
   If the status shows it is inactive or stopped, you can start the service using the following command:  
   ```sudo systemctl start docker```

## 1. Clone the Repository
   ```git clone https://github.com/buddhi530/todo-app.git  ```  
   navigate to project directory : ```cd todo-app```

## 2. Build and Run the Project
   ```docker compose up --build -d```

## 3. Verify Service Status
   ```docker ps```

## 4. Access the Application
   Frontend UI : Access the application in your web browser at: http://localhost:3000  
   Backend API : The API is running on http://localhost:8080

## 5. To verify the test execution and results:
   ```docker ps -a```

## 6. View the Unit tests and Integration tests for the backend test logs (results):
   ```docker logs coveragex-tester```

## 7. Stop and Clean Up
   ```docker compose down -v```
