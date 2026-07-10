// ============================================================
// ✅ ADD YOUR WORK HERE
// category: must match exactly one of the categories below
// For videos, use a thumbnail image (videos can't preview directly)
// ============================================================

// Build category tabs dynamically from project data


// Build gallery filtered by category
// Lightbox

document.addEventListener('click', (e) => {
  if (e.target.id === 'lightbox') closeLightbox();
});

document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape') closeLightbox();
});

// Smooth scroll + nav active animation
function scrollToSection(selector) {
  const target = document.querySelector(selector);
  if (!target) return;

  target.scrollIntoView({ behavior: 'smooth' });

  // Flash the clicked nav link
  document.querySelectorAll('.nav-links li').forEach(li => {
    const oc = li.getAttribute('onclick') || '';
    if (oc.includes(selector)) {
      li.classList.add('nav-active');
      setTimeout(() => li.classList.remove('nav-active'), 800);
    }
  });

  // Section entrance pop
  target.classList.add('section-flash');
  setTimeout(() => target.classList.remove('section-flash'), 600);
}

// Auto-highlight nav on scroll
const navObserver = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      const id = '#' + entry.target.id;
      document.querySelectorAll('.nav-links li').forEach(li => {
        const oc = li.getAttribute('onclick') || '';
        li.classList.toggle('nav-current', oc.includes(id));
      });
    }
  });
}, { threshold: 0.45 });

['hero','services','portfolio','about','contact'].forEach(id => {
  const el = document.getElementById(id);
  if (el) navObserver.observe(el);
});

// Init


// ═══════════════════════════════════════════
// UNIVERSAL SCROLL ANIMATION SYSTEM
// ═══════════════════════════════════════════

// Hero elements — add anim class with stagger delays
const heroTargets = [
  { sel: '.hero-label',   delay: 0   },
  { sel: '.hero h1',      delay: 0.1 },
  { sel: '.hero p',       delay: 0.2 },
  { sel: '.hero-actions', delay: 0.3 },
  { sel: '.hero-stats',   delay: 0.4 },
];
heroTargets.forEach(({ sel, delay }) => {
  const el = document.querySelector(sel);
  if (el) {
    el.classList.add('anim-up');
    el.style.transitionDelay = delay + 's';
  }
});

// All other animatable elements
document.querySelectorAll('.section-label, .section-title').forEach((el, i) => {
  el.classList.add('anim-up');
  el.style.transitionDelay = (i % 2 * 0.08) + 's';
});
document.querySelectorAll('.service-card').forEach((el, i) => {
  el.classList.add('anim-up');
  el.style.transitionDelay = (i * 0.08) + 's';
});
document.querySelectorAll('.tab-btn').forEach((el, i) => {
  el.classList.add('anim-up');
  el.style.transitionDelay = (i * 0.06) + 's';
});
document.querySelectorAll('.reveal-left, .contact-card').forEach((el, i) => {
  el.classList.add('anim-left');
  el.style.transitionDelay = (i * 0.08) + 's';
});
document.querySelectorAll('.reveal-right').forEach(el => el.classList.add('anim-right'));
document.querySelectorAll('.reveal-up').forEach(el => el.classList.add('anim-up'));
document.querySelectorAll('.tool-tag').forEach((el, i) => {
  el.classList.add('anim-up');
  el.style.transitionDelay = (i * 0.07) + 's';
});

// Single observer — re-animates EVERY time element enters/leaves viewport
const animObserver = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.remove('in-view');
      void entry.target.offsetWidth; // force reflow to restart transition
      entry.target.classList.add('in-view');

      // Skill bars re-fill on enter
      if (entry.target.closest && entry.target.closest('.about')) {
        document.querySelectorAll('.skill-bar-fill').forEach(bar => {
          bar.style.width = '0%';
          setTimeout(() => { bar.style.width = bar.dataset.w + '%'; }, 60);
        });
      }
    } else {
      // Reset when leaving — so it animates fresh next time
      entry.target.classList.remove('in-view');
      if (entry.target.closest && entry.target.closest('.about')) {
        document.querySelectorAll('.skill-bar-fill').forEach(bar => {
          bar.style.width = '0%';
        });
      }
    }
  });
}, { threshold: 0.08, rootMargin: '0px 0px -30px 0px' });

// Observe everything including hero elements
document.querySelectorAll('.anim-up, .anim-left, .anim-right').forEach(el => {
  animObserver.observe(el);
});

// ═══════════════════════════════════════════
// CONTACT FORM — WEB3FORMS
// ═══════════════════════════════════════════
const form = document.getElementById('contactForm');
if (form) {
  const submitBtn = form.querySelector('button[type="submit"]');
  const successEl = document.getElementById('formSuccess');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const originalText = submitBtn.querySelector('.btn-submit-text').textContent;

    // Button loading state
    submitBtn.querySelector('.btn-submit-text').textContent = 'Sending...';
    submitBtn.disabled = true;

    try {
      const response = await fetch("https://api.web3forms.com/submit", {
        method: "POST",
        body: formData
      });
      const data = await response.json();

      if (response.ok) {
        // ✅ Success
        submitBtn.style.display = 'none';
        successEl.style.display = 'block';
        successEl.style.color = '';
        successEl.textContent = "✦ Message sent! I'll get back to you within 24hrs.";
        form.reset();

        setTimeout(() => {
          submitBtn.style.display = 'flex';
          submitBtn.disabled = false;
          submitBtn.querySelector('.btn-submit-text').textContent = originalText;
          successEl.style.display = 'none';
        }, 4000);

      } else {
        // ❌ API error
        successEl.style.display = 'block';
        successEl.style.color = '#ff6b6b';
        successEl.textContent = '⚠ Error: ' + (data.message || 'Please try again.');
        submitBtn.querySelector('.btn-submit-text').textContent = originalText;
        submitBtn.disabled = false;
        setTimeout(() => { successEl.style.display = 'none'; successEl.style.color = ''; }, 4000);
      }

    } catch (error) {
      // ❌ Network error
      successEl.style.display = 'block';
      successEl.style.color = '#ff6b6b';
      successEl.textContent = '⚠ Network error. Please check your connection.';
      submitBtn.querySelector('.btn-submit-text').textContent = originalText;
      submitBtn.disabled = false;
      setTimeout(() => { successEl.style.display = 'none'; successEl.style.color = ''; }, 4000);
    }
  });
}

function handleFormSubmit(e) { e.preventDefault(); }

// ── CAMERA CURSOR ──
(function () {
  const cam = document.createElement('div');
  cam.id = 'cameraCursor';
  cam.textContent = '📷';
  document.body.appendChild(cam);

  let mouseX = window.innerWidth / 2;
  let mouseY = window.innerHeight / 2;
  let camX = mouseX;
  let camY = mouseY;

  document.addEventListener('mousemove', (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;
  });

  function animate() {
    camX += (mouseX - camX) * 0.12;
    camY += (mouseY - camY) * 0.12;
    cam.style.left = camX + 'px';
    cam.style.top  = camY + 'px';
    requestAnimationFrame(animate);
  }
  animate();
})();
