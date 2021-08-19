<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">No</th>
          <th scope="col">이름</th>
          <th scope="col">신분</th>
          <th scope="col">아이이름</th>
          <th scope="col">반</th>
          <th scope="col">전화번호</th>
          <th scope="col">삭제여부</th>
          <th scope="col">승인상태</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>Mark</td>
          <td>Otto</td>
          <td>@mdo</td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>Jacob</td>
          <td>Thornton</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td colspan="2">Larry the Bird</td>
          <td>@twitter</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name:'MyPageMaster'
}
</script>

<style scoped>
</style>