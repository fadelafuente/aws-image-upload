import logo from './logo.svg';
import './App.css';
import axios from "axios";
import React, {useState, useEffect} from 'react';

const UserProfiles = () => {
  const [UserProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return UserProfiles.map((userProfile, index) => {
    return <div key={index}>
      <h1>{userProfile.username}</h1>
      <p>{userProfile.userProfileId}</p>
    </div>
  });
};


function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
