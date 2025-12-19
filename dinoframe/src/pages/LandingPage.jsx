import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';

export default function LandingPage() {
    return (
        <div className="min-h-screen bg-slate-950 font-sans text-white scroll-smooth relative overflow-x-hidden">
            {/* Navbar */}
            <nav className="fixed w-full z-50 top-0 px-4 py-3">
                <div className="bg-white/70 dark:bg-black/70 backdrop-blur-md max-w-7xl mx-auto rounded-2xl px-6 py-3 shadow-sm flex flex-wrap items-center justify-between border border-white/20">
                    <Link to="/" className="flex items-center space-x-2 group">
                        <div className="bg-primary text-primary-foreground p-2 rounded-lg shadow-lg group-hover:rotate-12 transition-transform">
                            <svg className="h-6 w-6" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-1 17.93c-3.95-.49-7-3.85-7-7.93 0-.62.08-1.21.21-1.79L9 15v1c0 1.1.9 2 2 2v1.93zm6.9-2.54c-.26-.81-1-1.39-1.9-1.39h-1v-3c0-.55-.45-1-1-1H8v-2h2c.55 0 1-.45 1-1V7h2c1.1 0 2-.9 2-2v-.41c2.93 1.19 5 4.06 5 7.41 0 2.08-.8 3.97-2.1 5.39z" /></svg>
                        </div>
                        <span className="self-center text-xl font-bold tracking-tight">DinoBank</span>
                    </Link>

                    <div className="hidden md:flex space-x-8 font-medium text-slate-600 dark:text-slate-300">
                        <a href="#features" className="hover:text-primary transition">Özellikler</a>
                        <a href="#cards" className="hover:text-primary transition">Kartlar</a>
                        <a href="#contact" className="hover:text-primary transition">İletişim</a>
                    </div>

                    <div className="flex space-x-3">
                        <Link to="/login">
                            <Button className="bg-primary hover:bg-primary/90 text-primary-foreground font-semibold rounded-xl shadow-lg shadow-primary/20">
                                Giriş Yap
                            </Button>
                        </Link>
                    </div>
                </div>
            </nav>

            {/* Hero Section */}
            <section id="home" className="relative pt-40 pb-20 overflow-hidden">
                <div className="absolute top-20 right-0 w-[600px] h-[600px] bg-primary/10 rounded-full blur-[100px] -z-10"></div>
                <div className="absolute bottom-0 left-0 w-[400px] h-[400px] bg-blue-500/10 rounded-full blur-[80px] -z-10"></div>

                <div className="container mx-auto px-6 relative z-10">
                    <div className="flex flex-col lg:flex-row items-center gap-12">
                        <div className="w-full lg:w-1/2 space-y-8 text-center lg:text-left">
                            <div className="inline-block px-4 py-1.5 bg-white dark:bg-white/5 border border-primary/20 rounded-full shadow-sm">
                                <span className="text-primary font-bold text-xs uppercase tracking-wider flex items-center gap-2">
                                    <span className="w-2 h-2 bg-primary rounded-full animate-pulse"></span>
                                    Yeni Nesil Finans
                                </span>
                            </div>

                            <h1 className="text-5xl lg:text-7xl font-extrabold leading-[1.1]">
                                Paranız İçin <br />
                                <span className="text-transparent bg-clip-text bg-gradient-to-r from-primary to-emerald-400">Jurassic Bir Güç.</span>
                            </h1>

                            <p className="text-lg text-slate-600 dark:text-slate-400 max-w-xl mx-auto lg:mx-0 leading-relaxed">
                                Fosil bankacılık alışkanlıklarını geride bırakın. DinoBank ile ışık hızında transferler, kaya gibi sağlam güvenlik ve size özel avantajlar dünyasına adım atın.
                            </p>

                            <div className="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start">
                                <Link to="/register">
                                    <Button size="lg" className="rounded-2xl text-lg px-8 py-6 shadow-xl shadow-primary/20">
                                        Hesap Oluştur
                                    </Button>
                                </Link>
                                <Link to="/how-it-works">
                                    <Button variant="outline" size="lg" className="rounded-2xl text-lg px-8 py-6 bg-white/50 dark:bg-white/5 backdrop-blur-sm">
                                        Nasıl Çalışır?
                                    </Button>
                                </Link>
                            </div>

                            <div className="pt-8 flex items-center justify-center lg:justify-start gap-8 border-t border-slate-200 dark:border-slate-800">
                                <div>
                                    <p className="text-3xl font-bold">2M+</p>
                                    <p className="text-sm text-slate-500 font-medium">Kullanıcı</p>
                                </div>
                                <div className="w-px h-10 bg-slate-300 dark:bg-slate-700"></div>
                                <div>
                                    <p className="text-3xl font-bold">₺50Mr</p>
                                    <p className="text-sm text-slate-500 font-medium">Hacim</p>
                                </div>
                            </div>
                        </div>

                        {/* Right: Visual Composition */}
                        <div className="w-full lg:w-1/2 flex justify-center relative">
                            <div className="absolute inset-0 bg-gradient-to-tr from-primary/20 to-transparent rounded-full filter blur-3xl opacity-30 animate-pulse"></div>
                            <div className="relative w-full max-w-md animate-bounce duration-[3000ms]">
                                <div className="relative bg-slate-900 rounded-[3rem] p-4 shadow-2xl border-4 border-slate-800 ring-1 ring-slate-700">
                                    <div className="bg-white dark:bg-slate-950 rounded-[2.5rem] overflow-hidden h-[600px] w-full relative">
                                        <div className="bg-primary h-40 p-6 pt-10 text-primary-foreground rounded-b-[3rem] shadow-lg relative z-10">
                                            <div className="flex justify-between items-center mb-4">
                                                <div className="w-8 h-8 bg-white/20 rounded-full flex items-center justify-center text-xs">🦖</div>
                                                <div className="w-8 h-8 bg-white/20 rounded-full"></div>
                                            </div>
                                            <p className="text-primary-foreground/80 text-sm">Toplam Bakiye</p>
                                            <h3 className="text-3xl font-bold">₺ 124.500</h3>
                                        </div>

                                        <div className="p-6 space-y-4 -mt-6 relative z-0">
                                            <div className="bg-white dark:bg-slate-900 p-4 rounded-2xl shadow-lg border border-slate-100 dark:border-slate-800 flex items-center justify-between">
                                                <div className="flex items-center gap-3">
                                                    <div className="w-10 h-10 bg-green-50 dark:bg-green-900/20 text-primary rounded-full flex items-center justify-center font-bold">₺</div>
                                                    <div>
                                                        <p className="font-bold text-sm">Maaş Yatışı</p>
                                                        <p className="text-xs text-slate-400">DinoCorp A.Ş.</p>
                                                    </div>
                                                </div>
                                                <span className="font-bold text-emerald-500">+45.000₺</span>
                                            </div>

                                            <div className="bg-white dark:bg-slate-900 p-4 rounded-2xl shadow-sm border border-slate-100 dark:border-slate-800 flex items-center justify-between opacity-80">
                                                <div className="flex items-center gap-3">
                                                    <div className="w-10 h-10 bg-red-50 dark:bg-red-900/20 text-red-500 rounded-full flex items-center justify-center">🛒</div>
                                                    <div>
                                                        <p className="font-bold text-sm">Market</p>
                                                        <p className="text-xs text-slate-400">Migros</p>
                                                    </div>
                                                </div>
                                                <span className="font-bold text-slate-800 dark:text-slate-200">-1.200₺</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Features Section */}
            <section id="features" className="py-24 relative bg-slate-900 border-t border-slate-800">
                <div className="container mx-auto px-6">
                    {/* T-Rex Section */}
                    <div className="mb-32 flex flex-col md:flex-row items-center gap-12 bg-slate-800/50 p-12 rounded-[3rem] border border-slate-700 relative overflow-hidden">
                        <div className="absolute top-0 right-0 w-64 h-64 bg-primary/20 rounded-full blur-[100px] pointer-events-none"></div>

                        <div className="w-full md:w-1/3 flex justify-center">
                            <div className="w-64 h-64 bg-gradient-to-br from-emerald-500 to-primary rounded-full flex items-center justify-center text-[100px] shadow-2xl shadow-emerald-500/20 animate-bounce cursor-pointer hover:rotate-12 transition-transform">
                                🦖
                            </div>
                        </div>
                        <div className="w-full md:w-2/3 space-y-6 relative z-10">
                            <div className="inline-block px-4 py-1.5 bg-emerald-500/20 text-emerald-400 rounded-full text-xs font-bold uppercase tracking-wider">
                                Müşteri Temsilciniz
                            </div>
                            <h2 className="text-4xl font-extrabold text-white">
                                T-Rex İle Tanışın.
                            </h2>
                            <p className="text-lg text-slate-300 leading-relaxed">
                                "Merhaba! Ben T-Rex. Bankacılık işlemlerinizin fosilleşmesini engellemek için buradayım. Sürpriz ücretleri yiyor, faiz oranlarını ısırıyorum. Size en güçlü finansal desteği sunmak için 65 milyon yıldır bekliyorum!"
                            </p>
                            <Button className="bg-emerald-600 hover:bg-emerald-700 text-white rounded-xl px-8 h-12">
                                T-Rex İle Konuş (Yakında)
                            </Button>
                        </div>
                    </div>


                    <div className="text-center max-w-3xl mx-auto mb-16">
                        <span className="text-primary font-bold tracking-widest uppercase text-xs">Neden Biz?</span>
                        <h2 className="text-4xl font-bold mt-2 text-white">Dinozor Kadar Güçlü,<br />Teknoloji Kadar Hızlı.</h2>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                        {[
                            { icon: '🚀', title: 'Işık Hızı', desc: 'Havale, EFT ve FAST işlemleriniz saniyeler içinde gerçekleşir. DinoBank ile beklemek tarihe karıştı.', color: 'bg-green-50 dark:bg-green-900/20' },
                            { icon: '🛡️', title: 'Zırhlı Güvenlik', desc: 'Verileriniz, en son şifreleme teknolojileri ve Dinozor gücündeki güvenlik duvarlarıyla korunur.', color: 'bg-yellow-50 dark:bg-yellow-900/20' },
                            { icon: '🌍', title: 'Evrensel Erişim', desc: 'Dünyanın her yerinde geçerli kartlar ve düşük komisyonlu döviz işlemleri ile sınırlar kalktı.', color: 'bg-blue-50 dark:bg-blue-900/20' }
                        ].map((feature, i) => (
                            <div key={i} className="group bg-white dark:bg-slate-800 p-8 rounded-[2rem] shadow-lg border border-slate-100 dark:border-slate-700 hover:border-primary/30 transition-all duration-300 relative overflow-hidden">
                                <div className={`absolute top-0 right-0 w-32 h-32 ${feature.color} rounded-bl-[100px] -z-0 transition-transform group-hover:scale-110`}></div>
                                <div className="relative z-10">
                                    <div className="w-14 h-14 bg-primary text-primary-foreground rounded-2xl flex items-center justify-center mb-6 shadow-lg shadow-primary/20 text-2xl">
                                        {feature.icon}
                                    </div>
                                    <h3 className="text-xl font-bold mb-3">{feature.title}</h3>
                                    <p className="text-slate-500 dark:text-slate-400 leading-relaxed">{feature.desc}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </section >

            {/* Cards Section */}
            < section id="cards" className="py-24 bg-slate-900 border-t border-slate-800 overflow-hidden" >
                <div className="container mx-auto px-6">
                    <div className="text-center max-w-3xl mx-auto mb-16">
                        <span className="text-primary font-bold tracking-widest uppercase text-xs">Kartlarımız</span>
                        <h2 className="text-4xl font-bold mt-2">Cüzdanınıza Yakışan<br />Dinozor Gücü.</h2>
                    </div>

                    <div className="flex flex-wrap justify-center gap-8 perspective-1000">
                        {/* Card 1: Standard */}
                        <div className="group relative w-80 h-48 bg-gradient-to-br from-blue-600 to-blue-800 rounded-2xl shadow-2xl p-6 text-white transform transition-all duration-500 hover:scale-105 hover:-rotate-2 hover:shadow-blue-500/50">
                            <div className="flex justify-between items-start">
                                <span className="font-bold text-lg tracking-widest">DinoBank</span>
                                <span className="text-xs opacity-70">Debit</span>
                            </div>
                            <div className="mt-8 flex gap-2">
                                <div className="w-10 h-7 bg-yellow-400/80 rounded-md"></div>
                                <div className="w-7 h-7 border-2 border-white/30 rounded-full"></div>
                            </div>
                            <div className="mt-4">
                                <p className="font-mono text-lg tracking-widest opacity-90">**** **** **** 4242</p>
                            </div>
                            <div className="mt-4 flex justify-between items-end">
                                <div>
                                    <p className="text-[10px] opacity-70 uppercase">Kart Sahibi</p>
                                    <p className="font-medium text-sm tracking-wide">OSMAN YETKİN</p>
                                </div>
                                <div className="w-10 h-10 bg-white/20 rounded-full flex items-center justify-center text-xl">🦖</div>
                            </div>
                        </div>

                        {/* Card 2: Gold (Premium) */}
                        <div className="group relative w-80 h-48 bg-gradient-to-br from-yellow-500 via-amber-500 to-yellow-600 rounded-2xl shadow-2xl p-6 text-white transform transition-all duration-500 hover:scale-110 hover:z-10 hover:shadow-yellow-500/50">
                            <div className="absolute inset-0 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')] opacity-10"></div>
                            <div className="flex justify-between items-start relative z-10">
                                <span className="font-bold text-lg tracking-widest">DinoBank</span>
                                <span className="bg-white/20 px-2 py-0.5 rounded text-[10px] font-bold uppercase">Gold</span>
                            </div>
                            <div className="mt-8 flex gap-2 relative z-10">
                                <div className="w-10 h-7 bg-yellow-200/80 rounded-md"></div>
                                <span className="text-2xl">📶</span>
                            </div>
                            <div className="mt-4 relative z-10">
                                <p className="font-mono text-lg tracking-widest text-shadow-sm">5412 7512 3412 3456</p>
                            </div>
                            <div className="mt-4 flex justify-between items-end relative z-10">
                                <div>
                                    <p className="text-[10px] opacity-70 uppercase">Kart Sahibi</p>
                                    <p className="font-medium text-sm tracking-wide">KÜRŞAT YILMAZ</p>
                                </div>
                                <div className="text-2xl">🦕</div>
                            </div>
                        </div>

                        {/* Card 3: Black (Elite) */}
                        <div className="group relative w-80 h-48 bg-gradient-to-br from-slate-800 to-black rounded-2xl shadow-2xl p-6 text-white transform transition-all duration-500 hover:scale-105 hover:rotate-2 hover:shadow-primary/50 border border-slate-700">
                            <div className="absolute inset-0 bg-gradient-to-r from-transparent via-white/5 to-transparent skew-x-12 opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
                            <div className="flex justify-between items-start">
                                <span className="font-bold text-lg tracking-widest bg-gradient-to-r from-primary to-emerald-400 bg-clip-text text-transparent">DinoBank</span>
                                <span className="text-primary text-xs font-bold uppercase tracking-widest">Elite</span>
                            </div>
                            <div className="mt-8 flex gap-2">
                                <div className="w-10 h-7 bg-slate-400/80 rounded-md"></div>
                            </div>
                            <div className="mt-4">
                                <p className="font-mono text-lg tracking-widest opacity-90">4000 1234 5678 9010</p>
                            </div>
                            <div className="mt-4 flex justify-between items-end">
                                <div>
                                    <p className="text-[10px] opacity-70 uppercase">Kart Sahibi</p>
                                    <p className="font-medium text-sm tracking-wide">İLKMERT DEMİR</p>
                                </div>
                                <div className="text-2xl">🥚</div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Footer */}
            <footer className="bg-slate-50 dark:bg-slate-950 pt-16 pb-8 border-t border-slate-200 dark:border-slate-800">
                <div className="container mx-auto px-6">
                    <div className="flex flex-col md:flex-row justify-between items-center text-sm text-slate-500">
                        <div className="flex items-center space-x-2 mb-4 md:mb-0">
                            <span className="text-xl font-bold text-slate-900 dark:text-slate-100">DinoBank</span>
                        </div>
                        <p>&copy; 2025 DinoBank A.Ş. Tüm hakları saklıdır.</p>
                    </div>
                </div>
            </footer>
        </div>
    );
}
