// import React from 'react';

// const users = [
//   { id: 1, name: 'Alice', email: 'alice@example.com' },
//   { id: 2, name: 'Bob', email: 'bob@example.com' },
// ];

// function UserList() {
//   return (
//     <div>
//       <h1>User List</h1>
//       <ul>
//         {users.map((user) => (
//           <li key={user.id}>
//             <strong>Name:</strong> {user.name}, <strong>Email:</strong>{' '}
//             {user.email}
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// }

// export default UserList;

import { useState, useEffect } from 'react';
import axios from 'axios';
import './Users.css';

function UserList() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios
      .get('https://jsonplaceholder.typicode.com/users')
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }, []);

  return (
    <div className="user-list-container">
      <h1>User List</h1>
      <ul className="user-list">
        {users.map((user) => (
          <div className="user-item" key={user.id}>
            <strong>Name:</strong> {user.name}
            <strong>Email:</strong> {user.email}
            {user.company && <p className="user-company">{user.company.bs}</p>}
          </div>
        ))}
      </ul>
    </div>
  );
}

export default UserList;
