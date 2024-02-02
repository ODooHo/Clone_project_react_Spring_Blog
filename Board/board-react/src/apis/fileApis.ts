import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { getAccessTokenApi } from "./authApis";


const testUrl = 'http://localhost:8080'
const defaultUrl = 'http://15.165.24.146:8080'


export const profileUploadApi = async (token: string | null, refreshToken: string | null, data: any) => {
    const url = `${testUrl}/api/upload/profile`;
    const config = { method: 'post', url, data: data, headers: { "Content-Type": "multipart/form-data" } };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data || null;
}



export const getProfileApi = async (token: string | null, refreshToken: string | null, imageName: string | number) => {
    const url = `${testUrl}/api/images/${imageName}/profile`;
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    const imageUrl =  response?.data.data || null;
    displayImage(imageUrl);
    return imageUrl;
};

export const getImageApi = async (token: string | null, refreshToken: string | null, imageName: string | number) => {
    const url = `${testUrl}/api/images/${imageName}`;
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    const imageUrl =  response?.data.data || null;
    displayImage(imageUrl);
    return imageUrl;

};


export const getVideoApi = async (videoUrl: string) => {
    displayVideo(videoUrl);
    return videoUrl;
};

export const fileDownloadApi = async (token: string | null, refreshToken: string | null, fileName: string) => {
    const url = `${testUrl}/api/files/${fileName}`
    const config = { method: 'get', url };
    const response = await axiosRequest(config, token, refreshToken);
    return response?.data.data || null;

}

function displayImage(imageUrl: string) {
    const imgElement = document.createElement('img');
    imgElement.src = imageUrl;
    // Assuming you have a div with id "imageContainer" to display the image
    const imageContainer = document.getElementById('imageContainer');
    imageContainer?.appendChild(imgElement);
}

function displayVideo(videoUrl: string) {
    const videoElement = document.createElement('video');
    videoElement.src = videoUrl;
    videoElement.controls = true; // Show video controls (play, pause, etc.)
    videoElement.setAttribute('width', '640'); // Set video width
    videoElement.setAttribute('height', '480'); // Set video height

    const videoContainer = document.getElementById('videoContainer');
    videoContainer?.appendChild(videoElement);
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
}

