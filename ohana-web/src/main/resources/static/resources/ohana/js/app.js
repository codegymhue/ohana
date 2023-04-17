class App {
    static DOMAIN = location.origin;
    static BASE_URL_API = this.DOMAIN + "/api";
    static BASE_URL_POST = this.DOMAIN + "/api/dashboard";
    static BASE_URL_PROVINCE = "https://vapi.vnappmob.com/api/province";
    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/ohana123/image/upload";
    // static SCALE_IMAGE_W100_H80_Q100 = "c_limit,w_100,h_80,q_100";
    static SCALE_IMAGE_W600_H650_Q100 = "c_limit,w_600,h_650,q_100";

}

class User {
    constructor(id, fulLName, email, phone, address, description, locationRegion, balance) {
        this.id = id;
        this.fullName = fulLName;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.address = address;
        this.locationRegion = locationRegion;
        this.balance = balance;
    }
}

class Post {
    constructor(rentHouse, title, slug, descriptionContent, createdAt, location, user, thumbnailUrl, imageUrls, category, utilities, status) {
        this.rentHouse = rentHouse;
        this.title = title;
        this.slug = slug;
        this.descriptionContent = descriptionContent;
        this.createdAt = createdAt;
        this.location = location;
        this.user = user;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrls = imageUrls;
        this.category = category;
        this.utilities = utilities;
        this.status = status;
    }
}

class RentHouseDTO {
    constructor(price, capacity, area, gender) {
        this.price = price;
        this.capacity = capacity;
        this.area = area;
        this.gender = gender;
    }
}

class RentHouse {
    constructor(price, capacity, area, gender) {
        this.price = price;
        this.capacity = capacity;
        this.area = area;
        this.gender = gender;
    }
}

class LocationDTO {
    constructor(provinceId, provinceName, districtId, districtName, wardId, wardName, locationDetail, line1) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.line1 = line1;
        this.locationDetail = locationDetail;
    }
}

class LocationRegion {
    constructor(provinceId, provinceName, districtId, districtName, wardId, wardName, locationDetail) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.locationDetail = locationDetail;
    }
}

class PostDTO {
    constructor(title, categoryId, descriptionContent, images, location, rentHouse, poster, utilities, thumbnailId, id,idUser) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.descriptionContent = descriptionContent;
        this.thumbnailId = thumbnailId;
        this.images = images;
        this.location = location;
        this.rentHouse = rentHouse;
        this.poster = poster;
        this.utilities = utilities;
        this.idUser = idUser;

    }
}

class PostMedia {
    constructor(id, fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}

class Filter {
    constructor(priceStarts, priceEnds, utilities, categories, gender, wardId, provinceName, locationName, keyword) {
        this.priceStarts = priceStarts;
        this.priceEnds = priceEnds;
        this.utilities = utilities;
        this.categories = categories;
        this.gender = gender;
        this.wardId = wardId;
        this.provinceName = provinceName;
        this.locationName = locationName;
        this.keyword = keyword;

    }
}
function showSuccess(msg) {
    Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: msg,
        showConfirmButton: false,
        timer: 1500
    });
}

function showWarning(msg) {
    Swal.fire({
        title: msg,
        showClass: {
            popup: 'animate__animated animate__fadeInDown'
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutUp'
        }
    })
}

function showError(msg) {
    Swal.fire({
        position: 'mid-center',
        icon: 'error',
        title: msg,
        showConfirmButton: false,
        timer: 2500
    });
}

function loading() {
    //   Swal.fire({
    //       title: 'Custom width, padding, color, background.',
    //       width: 600,
    //       padding: '3em',
    //       color: '#716add',
    //       background: '#fff url(/images/trees.png)',
    //       backdrop: `
    //   rgba(0,0,123,0.4)
    //   url("/images/nyan-cat.gif")
    //   left top
    //   no-repeat
    // `
    //   })


    let timerInterval
    Swal.fire({
        title: 'Auto close alert!',
        html: 'I will close in <b></b> milliseconds.',
        timer: 2000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading()
            const b = Swal.getHtmlContainer().querySelector('b')
            timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft()
            }, 100)
        },
        willClose: () => {
            clearInterval(timerInterval)
        }
    }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('I was closed by the timer')
        }
    })
}