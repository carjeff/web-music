<!--
 * @Author: Zhang JunFeng
 * @Date: 2021-01-03
 -->
<template>
  <div class="singer">
    <header-top @input-data="searchData" @add-singer="addSinger" @multiple-delete="multipleDelete" />
    <el-card class="body">
      <el-table
        :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        style="width: 100%"
        size="small"
        @selection-change="handleSelectionChange"
        border
        stripe
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="pic" label="图片" min-width="5%" align="center">
          <template slot-scope="scope">
            <el-upload
              class="avatar-uploader"
              :action="uploadUrl(scope.row.id)"
              :before-upload="beforeUpload"
              :on-success="handleImgSuccess"
              name="file"
            >
              <img :src="scope.row.pic" class="avatar" />
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" min-width="8%" align="center" />
        <el-table-column prop="_sex" label="性别" min-width="5%" align="center" />
        <el-table-column prop="birth" label="生日" min-width="8%" align="center" />
        <el-table-column prop="location" label="地区" min-width="8%" align="center" />
        <el-table-column prop="introduction" show-overflow-tooltip label="简介" min-width="30%" align="center" />
        <el-table-column label="操作" min-width="10%" align="center">
          <template slot-scope="scope">
            <el-button
              @click="editSinger(scope.$index, scope.row)"
              size="mini"
              type="primary"
              icon="el-icon-edit"
              style="width: 50px; padding: 5px 0;"
              >编辑
            </el-button>
            <el-button
              @click="deleteSinger(scope.$index, scope.row)"
              size="mini"
              type="danger"
              icon="el-icon-delete"
              style="width: 50px; padding: 5px 0;"
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
    <singer-dialog
      ref="child"
      :is-edit-button="isEditButton"
      :edit-value="editValue"
      :dialog-visible="dialogFormVisible"
      @dialog-cancel="dialogCancel"
    />
  </div>
</template>

<script>
import HeaderTop from '@/components/common/HeaderTop'
import SingerDialog from '@/components/dialog/SingerDialog'

import { formatDate } from '@/utils/index'
import { pageSeparate } from '@/utils/mixin'

export default {
  name: 'SingerManage',
  components: {
    HeaderTop,
    SingerDialog
  },
  mixins: [pageSeparate],
  data() {
    return {
      tableData: [],
      dialogFormVisible: false,
      isEditButton: false,
      editValue: {},
      selectRows: []
    }
  },
  created() {
    this.getAllSingers()
  },
  methods: {
    // 获取所有歌手
    getAllSingers() {
      this.$http.singer.getAllSinger().then(res => {
        if (res.code === 0 && res.data) {
          let data = res.data
          data.forEach(item => {
            if (item.birth) {
              let time = new Date(item.birth)
              item.birth = formatDate(time, 'yyyy-MM-dd')
            }
            switch (item.sex) {
              case 0:
                item._sex = '女'
                break
              case 1:
                item._sex = '男'
                break
              case 2:
                item._sex = '组合'
                break
            }
          })
          this.tableData = res.data
        }
      })
    },
    // 模糊查询歌手
    searchData(val) {
      let query = {
        params: {
          name: val
        }
      }
      this.$http.singer.getSingerByName(query).then(res => {
        if (res.code === 0 && res.data) {
          let data = res.data
          data.forEach(item => {
            if (item.birth) {
              let time = new Date(item.birth)
              item.birth = formatDate(time, 'yyyy-MM-dd')
            }
            switch (item.sex) {
              case 0:
                item._sex = '女'
                break
              case 1:
                item._sex = '男'
                break
              case 2:
                item._sex = '组合'
                break
            }
          })
          this.tableData = res.data
        }
      })
    },
    // 添加歌手
    addSinger(val) {
      this.isEditButton = false
      this.dialogFormVisible = val
      this.editValue = {}
    },
    // 图片上传地址
    uploadUrl(id) {
      return `${process.env.VUE_APP_BASE_URL}/admin/singer/updatePic?id=${id}`
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
        this.$message.error('上传的头像图片大小必须小于 2M ')
        return false
      }
      return true
    },
    // 上传图片成功后
    handleImgSuccess(res) {
      if (res.code === 0) {
        this.getAllSingers()
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
    // 接收弹窗的取消事件
    dialogCancel() {
      this.dialogFormVisible = false
    },
    // 编辑歌手
    editSinger(index, row) {
      this.isEditButton = true
      this.dialogFormVisible = true
      this.editValue = row
    },
    // 存储选择行信息
    handleSelectionChange(rows) {
      this.selectRows = rows
    },
    // 删除歌手接口
    deleteSingerApi(data) {
      this.$http.singer
        .deleteSinger(JSON.stringify(data))
        .then(res => {
          if (res.code === 0) {
            this.$notify({
              message: '删除歌手成功',
              type: 'success'
            })
            this.getAllSingers()
          }
        })
        .catch(() => {
          this.$notify.error({
            message: '删除歌手失败'
          })
        })
    },
    // 批量删除
    async multipleDelete() {
      this.$confirm('此操作将永久删除歌手, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let data = []
          this.selectRows.forEach(item => {
            data.push(item.id)
          })
          await this.deleteSingerApi(data)
        })
        .catch(() => {
          this.$notify.error({
            message: '取消删除操作'
          })
        })
    },
    // 删除歌手信息
    async deleteSinger(index, row) {
      this.$confirm('此操作将永久删除歌手, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let data = []
          data.push(row.id)
          await this.deleteSingerApi(data)
        })
        .catch(err => {
          console.log(err)
          this.$notify.error({
            message: '取消删除操作'
          })
        })
    }
  }
}
</script>

<style>
.el-card__body {
  padding: 5px;
}
.el-tooltip__popper {
  max-width: 300px;
}
.avatar-uploader {
  width: 50px;
  height: 50px;
}
.el-upload--text {
  width: 50px;
  height: 50px;
}
.avatar {
  width: 50px;
  height: 50px;
  display: block;
}
</style>
