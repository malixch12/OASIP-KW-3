<template>
    <div>
        
        <div id="app" class="container my-3">
  <div class="row">
    <div class="col-12 text-center">
    </div>
    <div class="col-md-5 offset-md-1">
   
      <form>
        <div class="form-group">
          <label for="file">Select Image</label>
          <input type="file" @change="previewImage" class="form-control-file block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer  focus:outline-none" id="file">
    
          <div class="border p-2 mt-3 w-72">
            <p>Preview Here:</p>
            <template v-if="preview">
              <img :src="preview" class="img-fluid " />
              <p class="mb-0">file name: {{ image.name }}</p>
              <p class="mb-0">size: {{ image.size/1024 }}KB</p>
            </template>
          </div>
        </div>
      </form>
    </div>
    
    
    
    <div class="w-100"></div>
    <div class="col-12 mt-3 text-center">
      <button @click="reset">Clear file</button>
    </div>
  </div>
</div>
    </div>
  </template>
   
  <script>
  export default {
    data: function() {
    return {
      preview: null,
      image: null,
      preview_list: [],
      image_list: []
    };
  },
  methods: {
    previewImage: function(event) {
      var input = event.target;
      if (input.files) {
        var reader = new FileReader();
        reader.onload = (e) => {
          this.preview = e.target.result;
        }
        this.image=input.files[0];
        reader.readAsDataURL(input.files[0]);
      }
    },
    
    reset: function() {
      this.image = null;
      this.preview = null;
      this.image_list = [];
      this.preview_list = [];
      document.getElementById("file").value = "";
    }
  }
  }
  </script>
