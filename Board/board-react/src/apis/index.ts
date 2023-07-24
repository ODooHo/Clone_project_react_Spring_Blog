import axios from "axios";
import { type } from "os";

export const signInApi = async (data : any) => {

    const response = await axios.post("http://localhost:4000/api/auth/signIn", data).catch((error)=> null);
    if (!response){
        return null;
    }

    const result = response.data;
    return result
}

export const signUpApi = async (data: any) => {
    const response = await axios.post("http://localhost:4000/api/auth/signUp", data).catch((error)=> null);
    if (!response){
        return null;
    }

    const result = response.data;
    return result    
}


export const BoardApi = async (token: string, index : number) => {
    try {
      const response = await axios.get("http://localhost:4000/api/board/" + index, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      const result = response.data;
      return result;
    } catch (error) {
      console.error("Error fetching board data:", error);
      return null;
    }
  };

export const BoardTop3Api = async (token: string) => {
    try {
      const response = await axios.get("http://localhost:4000/api/board/top3", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      const result = response.data;
      return result;
    } catch (error) {
      console.error("Error fetching board data:", error);
      return null;
    }
  };
  
  export const BoardListApi = async (token: string) => {
    try {
      const response = await axios.get("http://localhost:4000/api/board/list", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      const result = response.data;
      return result;
    } catch (error) {
      console.error("Error fetching board data:", error);
      return null;
    }
  };

  export const MyPageApi = async (token: string) => {
    try {
      const response = await axios.get("http://localhost:4000/api/user/myPage", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      const result = response.data;
      return result;
    } catch (error) {
      console.error("Error fetching board data:", error);
      return null;
    }
  };


  export const BoardRegisterApi =  async (data: any, token: string) => {
    const response = await axios.post("http://localhost:4000/api/board/register", data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).catch((error)=> null);
    if (!response){
        return null;
    }

    const result = response.data;
    return result    
}

export const PatchUserApi =  async (data: any, token: string) => {
  const response = await axios.patch("http://localhost:4000/api/user/edit", data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }).catch((error)=> null);
  if (!response){
      return null;
  }

  const result = response.data;
  return result    
}

