import axios from 'axios'
import qs from 'qs';

export function login(data) {
  return axios.post('/sign/in',qs.stringify(data),{headers:{'Content-Type':'application/x-www-form-urlencoded'}})
}

export function getInfo(token) {
  return axios.get('/users/getUserInfo')
}

export function logout() {
  return axios.post('/sign/out')
}

