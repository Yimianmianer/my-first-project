<template>
  <div class="container">
    <h1>用户管理系统</h1>

    <!-- 添加/编辑表单 -->
    <div class="form">
      <input v-model="form.name" placeholder="姓名" />
      <input v-model="form.age" placeholder="年龄" type="number" />
      <input v-model="form.email" placeholder="邮箱" />
      <button @click="submitUser">{{ form.id ? '更新' : '添加' }}</button>
      <button v-if="form.id" @click="cancelEdit">取消</button>
    </div>

    <!-- 用户列表 -->
    <table>
      <thead>
        <tr><th>ID</th><th>姓名</th><th>年龄</th><th>邮箱</th><th>操作</th></tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.age }}</td>
          <td>{{ user.email }}</td>
          <td>
            <button @click="editUser(user)">编辑</button>
            <button @click="deleteUser(user.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      form: { id: null, name: '', age: '', email: '' }
    };
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      const res = await axios.get('http://localhost:8080/api/users');
      this.users = res.data;
    },
    async submitUser() {
      if (this.form.id) {
        // 更新
        await axios.put(`http://localhost:8080/api/users/${this.form.id}`, this.form);
      } else {
        // 新增
        await axios.post('http://localhost:8080/api/users', this.form);
      }
      this.resetForm();
      this.fetchUsers();
    },
    editUser(user) {
      this.form = { ...user }; // 复制对象，避免直接修改原数据
    },
    async deleteUser(id) {
      if (confirm('确定删除吗？')) {
        await axios.delete(`http://localhost:8080/api/users/${id}`);
        this.fetchUsers();
      }
    },
    resetForm() {
      this.form = { id: null, name: '', age: '', email: '' };
    },
    cancelEdit() {
      this.resetForm();
    }
  }
};
</script>

<style scoped>
.container {
  width: 800px;
  margin: 20px auto;
  font-family: Arial, sans-serif;
}
.form {
  margin-bottom: 20px;
}
.form input {
  margin-right: 10px;
  padding: 5px;
}
button {
  margin-right: 5px;
  padding: 5px 10px;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
}
th {
  background-color: #f2f2f2;
}
</style>