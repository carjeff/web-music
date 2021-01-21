<!--
 * @Author: Jeffrey
 * @Date: 2021-01-08
 -->

<template>
  <el-dialog :title="isEditButton ? '编辑歌曲信息' : '添加歌曲'" :visible="dialogVisible" :before-close="cancel">
    <el-card shadow="hover">
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>名字</label></el-col>
        <el-col :span="10">
          <el-input v-model="formData.name" size="mini" placeholder="请输入歌曲名" />
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>歌手名</label></el-col>
        <el-col :span="10">
          <el-input v-model="formData.singerName" size="mini" placeholder="请输入歌手名字" />
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg" :visible="isEditButton">
        <el-col :span="4" :offset="3"><label>上传/更新歌曲</label></el-col>
        <el-upload :action="upload(formData.id)" :show-file-list="false" :on-success="handleSongSuccess" :before-upload="beforeSongUpload">
          <div class="icon">
            <i class="el-icon-plus"></i>
          </div>
        </el-upload>
      </el-row>
      <el-row type="flex" align="middle" class="row-bg">
        <el-col :span="4" :offset="3"><label>简介</label></el-col>
        <el-col :span="18">
          <el-input v-model="formData.introduction" type="textarea" :rows="4" placeholder="请输入简介"> </el-input>
        </el-col>
      </el-row>
      <el-divider />
      <dialog-button :isEditButton="isEditButton" @add-button="addSong" @save-button="saveSong" @cancel-button="cancel" />
    </el-card>
  </el-dialog>
</template>

<script>
import DialogButton from '@/components/common/DialogButton'
export default {
  name: 'SongDialog',
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
        singerName: '',
        url: '',
        pic: '',
        introduction: '',
        singerId: ''
      }
    }
  },
  watch: {
    // 监听点击编辑按钮传递的参数
    editValue(newValue) {
      let _formData = {
        id: newValue.id,
        singerId: newValue.singerId,
        name: newValue.name,
        singerName: newValue.singerName,
        pic: newValue.pic,
        url: newValue.url,
        introduction: newValue.introduction
      }
      this.formData = _formData
    }
  },
  methods: {
    // 上传歌曲的函数
    upload(id) {
      if (this.isEditButton) return this.uploadSongUrl(id)
    },
    // 上传歌曲
    uploadSongUrl(id) {
      return `${process.env.VUE_APP_BASE_URL}/admin/songs/update_song?id=${id}`
    },
    // 在上传前进行校验
    beforeSongUpload(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = testmsg === 'mp3'
      if (!extension) {
        this.$message({
          message: '上传文件只能是mp3格式！',
          type: 'error'
        })
      }
      return extension
    },
    // 上传成功之后
    handleSongSuccess(res) {
      if (res.code === 0) {
        this.$notify({
          message: '上传成功',
          type: 'success'
        })
      } else {
        this.$notify({
          message: '上传失败',
          type: 'error'
        })
      }
    },
    // 新增歌曲信息
    addSong() {
      this.$http.songs
        .addSong(JSON.stringify(this.formData))
        .then(res => {
          if (res.code === 0) {
            this.$notify({
              message: '添加歌曲成功',
              type: 'success'
            })
            this.$parent.getAllSongsWithSingerName()
          }
        })
        .catch(err => {
          // console.log(err)
          this.$notify.error({
            message: err
          })
        })
      this.$emit('dialog-cancel') // 关闭弹窗
    },
    // 保存歌曲信息
    saveSong() {
      this.$http.songs
        .updateSong(JSON.stringify(this.formData))
        .then(res => {
          console.log(res.code)
          if (res.code === 0) {
            this.$notify({
              message: '歌曲信息更新成功',
              type: 'success'
            })
            this.$parent.getAllSongsWithSingerName()
          }
        })
        .catch(err => {
          // console.log(err)
          this.$notify.error({
            message: err
          })
        })
      this.$emit('dialog-cancel') // 关闭弹窗
    },
    // 关闭弹窗
    cancel() {
      this.$emit('dialog-cancel')
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
.icon {
  margin-top: 15px;
}
</style>
