import axios from "axios";

export const MyPageApi = async (token: string | null) => {
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

export const PatchUserApi = async (data: any, token: string | null) => {
    const response = await axios.patch("http://localhost:4000/api/user/edit", data, {
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
