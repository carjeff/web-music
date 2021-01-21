<!--
 * @Author: Zhang JunFeng
 * @Date: 2021-01-06
 -->
<template>
  <el-dialog :title="isEditButton ? '编辑歌手信息' : '新增歌手'" :visible="dialogVisible" :before-close="cancel">
    <el-card shadow="hover">
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>姓名</label></el-col>
        <el-col :span="10">
          <el-input v-model="formData.name" size="mini" placeholder="请输入歌手姓名" />
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>性别</label></el-col>
        <el-col :span="10">
          <el-radio v-model="formData.sex" :label="0">女</el-radio>
          <el-radio v-model="formData.sex" :label="1">男</el-radio>
          <el-radio v-model="formData.sex" :label="2">组合</el-radio>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>生日</label></el-col>
        <el-col :span="10">
          <el-date-picker v-model="formData.birth" value-format="yyyy-MM-dd" type="date" size="mini" placeholder="请选择日期" />
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>地区</label></el-col>
        <el-col :span="10"> <el-input v-model="formData.location" size="mini" placeholder="请输入地区"/></el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>简介</label></el-col>
        <el-col :span="18">
          <el-input v-model="formData.introduction" type="textarea" :rows="4" placeholder="请输入简介"> </el-input>
        </el-col>
      </el-row>
      <el-divider />
      <dialog-button :isEditButton="isEditButton" @add-button="addSinger" @save-button="saveSinger" @cancel-button="cancel" />
    </el-card>
  </el-dialog>
</template>

<script>
import DialogButton from '@/components/common/DialogButton'
export default {
  name: 'SingerDialog',
  components: {
    DialogButton
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    isEditButton: {
      type: Boolean,
      default: false
    },
    editValue: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      formData: {
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      }
    }
  },
  watch: {
    // 监听点击编辑按钮传递的参数
    editValue(newValue) {
      let _formData = {
        id: newValue.id,
        name: newValue.name,
        sex: newValue.sex,
        birth: newValue.birth,
        location: newValue.location,
        introduction: newValue.introduction
      }
      this.formData = _formData
    }
  },
  methods: {
    // 新增歌手信息
    addSinger() {
      if (this.checkForm()) {
        this.formData.pic = '/img/singerPic/avatar.png'
        this.$http.singer
          .addSinger(JSON.stringify(this.formData))
          .then(res => {
            if (res.code === 0) {
              this.$notify({
                message: '添加歌手成功',
                type: 'success'
              })
              this.$parent.getAllSingers()
            }
          })
          .catch(() => {
            this.$notify.error({
              message: '添加歌手失败'
            })
          })
        this.$emit('dialog-cancel') // 关闭弹窗
      }
    },
    // 保存歌手信息
    saveSinger() {
      if (this.checkForm()) {
        this.$http.singer
          .updateSinger(JSON.stringify(this.formData))
          .then(res => {
            if (res.code === 0) {
              this.$notify({
                message: '歌手信息更新成功',
                type: 'success'
              })
              this.$parent.getAllSingers()
            }
          })
          .catch(() => {
            this.$notify.error({
              message: '歌手信息更新失败'
            })
          })
        this.$emit('dialog-cancel') // 关闭弹窗
      }
    },
    // 关闭弹窗
    cancel() {
      this.$emit('dialog-cancel')
    },
    // 检查表单是否符合要求
    checkForm() {
      if (this.formData.name === '' || typeof this.formData.name === 'undefined') {
        alert('歌手姓名不能为空！')
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 10px;
}

.el-col {
  border-radius: 4px;
}
.el-radio {
  margin-right: 20px;
}
.row-bg {
  padding: 8px 8px;
  background-color: #fbfbfb85;
  border-radius: 4px;
}
</style>
