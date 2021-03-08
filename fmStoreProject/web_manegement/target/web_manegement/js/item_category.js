new Vue({
    el: "#app",
    data:{
        categoryList:[],
        grade:1,
        gradeEntity1:{},
        gradeEntity2:{}
    },
    methods: {
        selectCateByParentId: function (id) {
            _this = this;
            axios.post("/itemCat/findByParentId.do?parentId="+id)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.categoryList =response.data;
                    console.log(response);
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        nextGrade:function (item) {
            if (this.grade == 1){
                this.gradeEntity1 = {};
                this.gradeEntity2 = {};
            }
            if (this.grade == 2){
                this.gradeEntity1 = item;
                this.gradeEntity2 = {};
            }
            if (this.grade == 3){
                this.gradeEntity2 = item;
            }
            this.selectCateByParentId(item.id);
        },
        setGrade:function (grade) {
            this.grade = grade;
        }

    },
    created: function () {
        this.selectCateByParentId(0);
    }
});