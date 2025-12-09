import React from "react";

export default function TaskCard({ task, onDone }) {
  return (
    <div className="task-card">
      <div className="task-info">
        <h3>{task.title}</h3>
        <p>{task.description}</p>
      </div>

      <button className="done-btn" onClick={() => onDone(task.id)}>
        Done
      </button>
    </div>
  );
}
