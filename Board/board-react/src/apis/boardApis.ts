import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { getAccessTokenApi } from "./authApis";

const testUrl = 'http://localhost:8080'
const defaultUrl = 'http://15.165.24.146:8080'

export const BoardApi = async (token: string | null, refreshToken: string | null, index: number) => {
    const url = `${testUrl}/api/board/${index}`;
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

export const BoardIncreaseApi = async (token: string | null, refreshToken: string | null, index: number) => {
    const url = `${testUrl}/api/board/${index}`;
    const config = { method: 'post', url, data: 1, headers: { 'Content-Type': 'application/json' } };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};



export const BoardTop3Api = async (token: string | null, refreshToken: string | null) => {
    const url = `${testUrl}/api/board/top3`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

export const BoardListApi = async (token: string | null, refreshToken: string | null) => {
    const url = `${testUrl}/api/board/list`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

export const BoardRegisterApi = async (token: string | null, refreshToken: string | null, data: any,) => {
    const url = `${testUrl}/api/board/register`;
    const config = { method: 'post', url, data: data, headers: { "Content-Type": "multipart/form-data" } };
    const response = await axiosRequest(config, token, refreshToken);
    console.log(response)
    return response?.data || null;
}

export const BoardDeleteApi = async (token: string | null, refreshToken: string | null, index: number) => {
    const url = `${testUrl}/api/board/${index}/delete`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

export const boardEditApi = async (token: string | null, refreshToken: string | null, boardId: number, data: any) => {
    const url = `${testUrl}/api/board/${boardId}/edit`
    const config = { method: 'patch', url, data: data  ,headers: { 'Content-Type': 'application/json' }};
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;

}



const axiosRequest = async (config: AxiosRequestConfig, token: string | null, refreshToken: string | null) => {
    try {
        return await axios({ ...config, headers: { ...config.headers, Authorization: `Bearer ${token}` } });
    } catch (error) {
        const axiosError = error as AxiosError;
        if (axiosError.response && axiosError.response.status === 401 && refreshToken) {
            try {
                const refreshResponse = await getAccessTokenApi(refreshToken);

                if (refreshResponse.data) {
                    const newToken = refreshResponse.data.token;
                    const newConfig = { ...config, headers: { ...config.headers, Authorization: `Bearer ${newToken}` } };
                    return await axios(newConfig);
                } else {
                    console.error("Refresh token is expired or invalid");
                    return null;
                }
            } catch (refreshError) {
                console.error("Error refreshing access token:", refreshError);
                return null;
            }
        }
        console.error("Error refreshing access token:", axiosError);
        return null;
    }
};