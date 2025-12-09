import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api/v1/todo",
});

export const fetchTasks = (limit = 5) =>
  API.get(`/getTasks/${limit}`).then((res) => res.data);

export const createTask = (task) =>
  API.post("/createTask", task).then((res) => res.data);

export const completeTask = (id) => API.patch(`/task/complete/${id}`);
