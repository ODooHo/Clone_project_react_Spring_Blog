import axios from "axios";

export const BoardApi = async (token: string | null, index: number) => {
    const url = `http://localhost:4000/api/board/${index}`
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

export const BoardIncreaseApi = async (token: string | null, index: number) => {
    const url = `http://localhost:4000/api/board/${index}`

    const data = 1;
    const response = await axios.post(url, data, {
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
    }).catch((error) => null);
    if (!response) {
        return null;
    }

    const result = response.data;
    return result
};



export const BoardTop3Api = async (token: string | null) => {
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

export const BoardListApi = async (token: string | null) => {
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

export const BoardRegisterApi = async (data: any, token: string | null) => {
    const response = await axios.post("http://localhost:4000/api/board/register", data, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    }).catch((error) => null);
    if (!response) {
        return null;
    }

    const result = response.data;
    return result
}

export const deleteBoardApi = async (token: string | null, index: number) => {
    const url = `http://localhost:4000/api/board/${index}/delete`
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