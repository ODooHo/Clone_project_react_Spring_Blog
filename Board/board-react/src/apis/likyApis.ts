import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { getAccessTokenApi } from "./authApis";



const testUrl = 'http://localhost:8080'
const defaultUrl = 'http://15.165.24.146:8080'

export const LikyApi = async (token: string | null, refreshToken : string| null ,index: number) => {
    const url = `${testUrl}/api/board/${index}/liky/get`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

export const getLikyCountApi = async (token: string | null, refreshToken : string| null , index: number) => {
    const url = `${testUrl}/api/board/${index}/liky/get/count`
    

}

export const likyControlApi = async (token: string | null, refreshToken : string| null , boardId: number) => {
    const url = `${testUrl}/api/board/${boardId}/liky`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
};

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
}