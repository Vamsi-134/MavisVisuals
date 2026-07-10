async function loadGallery() {

    try {

        const response = await fetch("/api/gallery");

        const media = await response.json();

        console.log(media);

    } catch (error) {

        console.error(error);

    }

}

loadGallery();