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



export const getImageApi = async (token: string | null, imageName: string) => {
    const url = `http://localhost:4000/api/images/${imageName}.jpg`;
    try {
        const response = await axios.get(url, {
            headers: {
                Authorization: `Bearer ${token}`,
                responseType: "blob",
            },
        });

        const result = response.data;
        return result;
    } catch (error) {
        console.error("Error fetching board data:", error);
        return null;
    }
};