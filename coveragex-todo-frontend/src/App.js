import React, { useEffect, useState } from "react";
import { fetchTasks, createTask, completeTask } from "./api/api";
import TaskCard from "./components/TaskCard";
import "./App.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [complete, setComplete] = useState(false);
  const [loading, setLoading] = useState(false);

  const loadTasks = async () => {
    setLoading(true);
    const data = await fetchTasks(5);
    // console.log(data);
    const safeTasks= data?.payload?.[0] ? data.payload[0] : [];
    setTasks(safeTasks);
    setLoading(false);
  };

  useEffect(() => {
    loadTasks();
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!title.trim()) {
      toast.error("Title is required!");
      return;
    }

    await createTask({
      title: title.trim(),
      description: description.trim(),
      complete:false
      
    });

    setTitle("");
    setDescription("");

    toast.success("Task added successfully!");
    loadTasks();
  };

  const handleDone = async (id) => {
    await completeTask(id);
    loadTasks();
  };

  return (
    <div className="todo-container">

      <ToastContainer 
      position="top-right" 
      autoClose={3000} 
      hideProgressBar={false} 
      newestOnTop={true} 
      closeOnClick 
      rtl={false} 
      pauseOnFocusLoss 
      draggable 
      pauseOnHover 
    />
  <div className="todo-box">

    {/* ADD TASK */}
    <div className="task-form-section">
      <h2>Add a Task</h2>

      <form onSubmit={handleCreate} className="task-form">
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <textarea
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <button type="submit" className="add-btn">Add</button>
      </form>
    </div>

    {/* TASK LIST */}
    <div className="task-list-section">
      {loading ? (
        <p>Loading tasks…</p>
      ) : tasks.length === 0 ? (
        <p>No tasks available</p>
      ) : (
        tasks.map((task) => (
          <TaskCard key={task.id} task={task} onDone={handleDone} />
        ))
      )}
    </div>

  </div>
</div>
  )
}

export default App;
