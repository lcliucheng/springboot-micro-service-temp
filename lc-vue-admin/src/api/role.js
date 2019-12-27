import axios from "axios/index";

export function getFunction(roleId) {
  return axios.get('/users/getUserFunctions');
}
export function getDicts() {
  return axios.get('/dicts/all');
}

