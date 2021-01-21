<!--
 * @Author Jeffrey
 * @Date: 2021-01-08 
-->
<template>
  <div class="song">
    <Header>
      <el-button slot="left" size="mini" type="primary" @click="addSong" round icon="el-icon-plus">添加歌曲</el-button>
      <el-select slot="right" size="mini" v-model="selectValue" placeholder="搜索类别">
        <el-option label="歌曲名" value="歌曲名"></el-option>
        <el-option label="歌手名" value="歌手名"></el-option>
        <el-option label="简介" value="简介"></el-option>
      </el-select>
      <el-input placeholder="请输入内容" v-model="searchValue" slot="right" size="mini" clearable @clear="search">
        <el-button slot="append" icon="el-icon-search" @click="search">搜索</el-button>
      </el-input>
    </Header>
    <el-card class="body">
      <el-table
        :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        style="width: 100%"
        size="small"
        border
        stripe
      >
        <el-table-column prop="pic" label="图片" min-width="3%" align="center">
          <template slot-scope="scope" align="center">
            <el-upload
              class="image-uploader"
              :action="uploadUrl(scope.row.id)"
              :before-upload="beforeUpload"
              :on-success="handleImgSuccess"
              name="file"
            >
              <img :src="scope.row.pic" class="image" />
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="歌名" min-width="5%" align="center" />
        <el-table-column prop="singerName" label="歌手" min-width="5%" align="center" />
        <el-table-column prop="url" label="路径" min-width="10%" align="center" />
        <el-table-column prop="introduction" label="简介" min-width="30%" align="center" />
        <el-table-column label="操作" min-width="10%" align="center">
          <template slot-scope="scope">
            <el-button
              @click="editSong(scope.$index, scope.row)"
              size="mini"
              type="primary"
              icon="el-icon-edit"
              style="width: 50px; padding: 7px 0;"
              >编辑
            </el-button>
            <el-button
              @click="deleteSong(scope.$index, scope.row)"
              size="mini"
              type="danger"
              icon="el-icon-delete"
              style="width: 50px; padding: 7px 0;"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <page-separate
      :page-sizes="pageSizes"
      :page-size="pageSize"
      :total-count="tableData.length"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <song-dialog
      ref="child"
      :is-edit-button="isEditButton"
      :edit-value="editValue"
      :dialog-visible="dialogFormVisible"
      @dialog-cancel="dialogCancel"
    />
    <add-song-dialog
      ref="child"
      :is-edit-button="isEditButton"
      :edit-value="editValue"
      :dialog-visible="addSongDialogFormVisible"
      @dialog-cancel="dialogCancel"
    />
  </div>
</template>

<script>
import Header from '@/components/common/Header'
import { pageSeparate } from '@/utils/mixin'
import SongDialog from '@/components/dialog/SongDialog'
import AddSongDialog from '@/components/dialog/AddSongDialog'

export default {
  name: 'SongManage',
  components: {
    Header,
    SongDialog,
    AddSongDialog
  },
  mixins: [pageSeparate],
  data() {
    return {
      tableData: [],
      isEditButton: false,
      dialogFormVisible: false,
      addSongDialogFormVisible: false,
      editValue: {},
      selectRows: [],
      selectValue: '',
      searchValue: ''
    }
  },
  created() {
    this.getAllSongsWithSingerName()
  },
  methods: {
    // 图片上传地址
    uploadUrl(id) {
      return `${process.env.VUE_APP_BASE_URL}/admin/songs/update_pic?id=${id}`
    },
    // 校验图片格式
    beforeUpload(file) {
      const isPic = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isPic) {
        this.$message.error('上传的头像图片必须为 jpg 或 png 格式')
        return false
      }

      const size = file.size / 1024 / 1024
      if (size >= 2) {
        this.$message.error('上传的图片大小必须小于 2M ')
        return false
      }
      return true
    },
    // 上传图片成功后
    handleImgSuccess(res) {
      if (res.code === 0) {
        this.getAllSongsWithSingerName()
        this.$notify({
          message: '图片上传并更新成功',
          type: 'success'
        })
      } else {
        this.$notify.error({
          message: '图片上传失败'
        })
      }
    },
    // 添加歌曲
    addSong() {
      this.isEditButton = false
      this.addSongDialogFormVisible = true
      this.editValue = {}
    },
    // 查找
    search() {
      let val = this.searchValue
      if (this.selectValue === '歌曲名') this.searchBySong(val)
      else if (this.selectValue === '歌手名') this.searchBySinger(val)
      else this.searchByIntroduction(val)
    },
    // 查找歌曲
    searchBySong(val) {
      this.$http.songs
        .getSongsByName({
          params: {
            name: val
          }
        })
        .then(res => {
          if (res.code === 0 && res.data) {
            this.tableData = res.data
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 查找歌手对应歌曲
    searchBySinger(val) {
      this.$http.songs
        .getSongsBySingerName({
          params: {
            name: val
          }
        })
        .then(res => {
          if (res.code === 0 && res.data) {
            this.tableData = res.data
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 根据简介查找歌曲
    searchByIntroduction(val) {
      this.$http.songs
        .getSongsByIntroduction({
          params: {
            keyword: val
          }
        })
        .then(res => {
          if (res.code === 0 && res.data) {
            this.tableData = res.data
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 编辑歌曲信息
    editSong(index, row) {
      this.isEditButton = true
      this.dialogFormVisible = true
      this.editValue = row
    },
    // 接收弹窗的取消事件
    dialogCancel() {
      this.dialogFormVisible = false
      this.addSongDialogFormVisible = false
    },
    // 获取带歌手名的歌曲列表
    getAllSongsWithSingerName() {
      this.$http.songs.getAllSongsWithSingerName().then(res => {
        if (res.code === 0 && res.data) {
          res.data.forEach(element => {
            // console.log(element)
            if (!element.pic) element.pic = '/img/songPic/tubiao.jpg'
          })
          this.tableData = res.data
        }
      })
    },
    // 删除歌曲时的API操作
    deleteSongApi(data) {
      let query = {
        params: {
          id: data
        }
      }
      this.$http.songs
        .deleteSong(query)
        .then(res => {
          if (res.code === 0) {
            this.$notify({
              message: '删除歌曲成功',
              type: 'success'
            })
            this.getAllSongsWithSingerName()
          }
        })
        .catch(err => {
          this.$notify.error({
            message: err + ': 删除歌曲失败'
          })
        })
    },
    // 删除歌手信息
    async deleteSong(index, row) {
      this.$confirm('此操作将永久删除歌曲, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let data = row.id
          await this.deleteSongApi(data)
        })
        .catch(err => {
          console.log(err)
          this.$notify.error({
            message: err + ': 取消删除操作'
          })
        })
    }
  }
}
</script>

<style>
.image-uploader {
  width: 50px;
  height: 50px;
}
.el-upload--text {
  width: 50px;
  height: 50px;
}
.image {
  width: 50px;
  height: 50px;
  display: block;
}
</style>
