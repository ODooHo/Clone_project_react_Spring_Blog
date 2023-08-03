import axios from "axios";

export const LikyApi = async (token: string | null, index: number) => {

    const url = `http://localhost:4000/api/board/${index}/liky/get`
    try {
        const response = await axios.get(url, {
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

export const getLikyCountApi = async (token: string | null, index: number) => {

    const url = `http://localhost:4000/api/board/${index}/liky/get/count`
    try {
        const response = await axios.get(url, {
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

export const LikyRegisterApi = async (data: any, token: string | null, index: number) => {

    const url = `http://localhost:4000/api/board/${index}/liky/add`
    try {
        const response = await axios.post(url, data, {
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
}

export const deleteLikyApi = async (token: string | null, boardNumber: number , likeUserNickname : string) => {
    const url = `http://localhost:4000/api/board/${boardNumber}/liky/delete/${likeUserNickname}`
    try {
        const response = await axios.get(url, {
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