import axios from "axios";

export const SearchBoardApi = async (data: any, token: string | null) => {
    const response = await axios.post("http://localhost:4000/api/search"  , data, {
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

export const PopularSearchApi = async (token: string | null) => {
    const url = `http://localhost:4000/api/search/popularSearchList`
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