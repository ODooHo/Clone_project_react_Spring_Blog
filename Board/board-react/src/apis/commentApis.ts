import axios from "axios";

export const CommentRegisterApi = async (data: any, token: string | null, index: number) => {
    const url = `http://localhost:4000/api/board/${index}/comment/register`
    const response = await axios.post(url, data, {
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

export const CommentListApi = async (token: string | null, index: number) => {
    const url = `http://localhost:4000/api/board/${index}/comment/list`;
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

export const deleteCommentApi = async (token: string | null, boardNumber: number, commentId: number) => {
    const url = `http://localhost:4000/api/board/${boardNumber}/comment/${commentId}/delete`
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