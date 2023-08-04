import axios from "axios";

export const profileUploadApi = async (data: any, token: string | null) => {
    const response = await axios.post("http://localhost:4000/api/upload/profile", data, {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
        },
    }).catch((error) => null);
    if (!response) {
        return null;
    }

    const result = response.data;
    return result
}



export const getImageApi = async (token: string | null, imageName: string | number) => {
    const url = `http://localhost:4000/api/images/${imageName}`;
    try {
        const response = await axios.get(url, {
            headers: {
                Authorization: `Bearer ${token}`,
            },

            responseType : 'blob'
        });

        const imageUrl = URL.createObjectURL(response.data);
        return imageUrl;
    } catch (error) {
        console.error("Error fetching board data:", error);
        return null;
    }
};

export const getVideoApi = async (token: string | null, videoName: string | number) => {
    const url = `http://localhost:4000/api/videos/${videoName}`;
    try {
        const response = await axios.get(url, {
            headers: {
                Authorization: `Bearer ${token}`,
            },

            responseType : "blob",
        });

        console.log(response.data)

        const videoUrl = URL.createObjectURL(response.data);
        console.log(videoUrl)
        return videoUrl;
    } catch (error) {
        console.error("Error fetching board data:", error);
        return null;
    }
};

export const fileDownloadApi = async (token: string | null, fileName : number) => {
    const response = await axios.get(`http://localhost:4000/api/files/${fileName}`,  {
        headers: {
            Authorization: `Bearer ${token}`,
        },

        responseType : 'blob'
    }).catch((error) => null);
    if (!response) {
        return null;
    }

    const result = response.data;
    return result
}