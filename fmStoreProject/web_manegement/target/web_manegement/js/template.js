Vue.component('v-select', VueSelect.VueSelect);

new Vue({
    el: "#app",
    data: {
        tempList: [],
        temp:{},
        searchTemp:{},

        page: 1,
        pageSize: 5,
        total: 0,
        maxPage: 9,

        placeholder: '可以进行多选',
        brandsOptions: [],
        selectBrands: [],
        sel_brand_obj: [],

        addname:"",

        specOptions: [],
        selectSpecs: [],
        sel_spec_obj: []

    },
    methods:{
        pageHandler: function (page) {
            _this = this;
            this.page = page;
            axios.post("/temp/search.do?page="+page+"&pageSize="+this.pageSize,this.searchTemp)
                .then(function (response) {
                    //取服务端响应的结果
                    _this.tempList = response.data.rows;
                    _this.total = response.data.total;

                  console.log(response.data);
                    console.log(response);
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        josnToStr:function (jsonStr, key) {
            var jsonObj = JSON.parse(jsonStr);
            var value = '';
            for (var i = 0; i < jsonObj.length; i++){
                if (i > 0){
                    value += ",";
                }
                value += jsonObj[i][key];
            }
            return value;
        },
        selected_brand: function(values){
           /* this.selectBrands =values.map(function(obj){
                return obj.id
            });*/
            console.log(this.sel_brand_obj)
        },
        selLoadData:function () {
            _this = this;
            axios.get("/brand/selectOptionList.do")
                .then(function (response) {
                    _this.brandsOptions = response.data;
                }).catch(function (reason) {
                console.log(reason);
            });
            axios.get("/spec/selectOptionList.do")
                .then(function (response) {
                    _this.specOptions = response.data;
                }).catch(function (reason) {
                console.log(reason);
            });
        },
        selected_spec: function(values){
            this.selectSpecs =values.map(function(obj){
                return obj.id
            });
            console.log(this.sel_spec_obj);
        },

        save:function () {
            var entity = {
                name:this.addname,
                specIds:this.sel_spec_obj,
                brandIds:this.sel_brand_obj
            };
            axios.post("/temp/add.do",entity)
                .then(function (response) {
                    console.log(response);
                    if (response.data.success){
                        _this.pageHandler(1);
                    } else{
                        alert(response.data.message);
                    }
                }).catch(function (reason) {
                console.log(reason);
            });
        }




    },

    created: function() {
        this.pageHandler(1);
        this.selLoadData();
    }

});