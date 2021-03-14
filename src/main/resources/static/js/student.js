const d = document,
    $editButtons = d.querySelectorAll('.editButton'),
    $editModal = d.querySelector('#editModal'),
    $detailsModal = d.querySelector('#detailsModal'),
    $detailsButtons = d.querySelectorAll('.detailsButton'),
    $deleteModal = d.querySelector('#deleteModal'),
    $deleteButtons = d.querySelectorAll('.deleteButton');


/* Edit Modal */
$editButtons.forEach(($editButton) => {
    $editButton.addEventListener("click", (e) => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        getAll(link);
    });
});

const getAll = async (link) => {
    try {
        let res = await fetch(`http://localhost:8080${link}`);
        let json = await res.json();
        console.log(json);
        if (!res.ok) throw {
            status: res.status,
            statusText: res.statusText
        };
        $editModal.querySelector('#idEdit').value = json.id;
        $editModal.querySelector('#nameEdit').value = json.name;
        $editModal.querySelector('#lastnameEdit').value = json.lastname;
        $editModal.querySelector('#dniEdit').value = json.dni;
        $editModal.querySelector('#legajoEdit').value = json.legajo;

    } catch (err) {

    }
    $editModal.classList.add('show')
    $editModal.style = 'display:block';
}


$editModal.addEventListener('click', e => {
    if(e.target.matches('.close *') || e.target.matches('.close-button')){
        $editModal.classList.remove('show');
        $editModal.style = 'display:none';
    }
})

/* Details Modal */

$detailsButtons.forEach(($detailButton) => {
    $detailButton.addEventListener('click', e => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        getTeacher(link);
    })
})

const getTeacher = async (link) => {

    try {
        let res = await fetch(`http://localhost:8080${link}`)
        let json = await res.json();

        $detailsModal.querySelector('#nameDetails').value = json.name;
        $detailsModal.querySelector('#lastnameDetails').value = json.lastname;
        $detailsModal.querySelector('#dniDetails').value = json.dni;
        $detailsModal.querySelector('#legajoDetails').value = json.legajo;
    }catch(err){

    }
    $detailsModal.classList.add('show')
    $detailsModal.style = 'display:block';
}

$detailsModal.addEventListener('click', e => {
    if(e.target.matches('.close *') || e.target.matches('.close-button')){
        $detailsModal.classList.remove('show');
        $detailsModal.style = 'display:none';
    }
})




/* Delete Modal */

$deleteButtons.forEach(($deleteButton) => {
    $deleteButton.addEventListener('click', e => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        $deleteModal.querySelector('#confirmDeleteButton')
            .setAttribute('href',`http://localhost:8080${link}`);
        $deleteModal.classList.add('show');
        $deleteModal.style = 'display:block';
    })
})


$deleteModal.addEventListener('click', e => {
    if(e.target.matches('.close *') || e.target.matches('.cancel-button')){
        $deleteModal.classList.remove('show');
        $deleteModal.style = 'display:none';
    }
})