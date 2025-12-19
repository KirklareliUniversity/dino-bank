import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { register } from '@/lib/api';
import { Loader2, ArrowLeft } from 'lucide-react';
import dinoSkeleton from '@/assets/dino-skeleton.png';

export default function RegisterPage() {
    const [formData, setFormData] = useState({
        ad: '', soyad: '', email: '', tcKimlikNo: '', telefon: '', dogumTarihi: '', sifre: '', adres: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.id]: e.target.value });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        try {
            await register(formData);
            localStorage.removeItem('token');
            localStorage.removeItem('dinoUser');
            navigate('/login');
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex bg-slate-950 text-white relative overflow-hidden">
            {/* Back to Home Button */}
            <div className="absolute top-6 left-6 z-50">
                <Link to="/">
                    <Button variant="ghost" className="text-white hover:bg-white/10 gap-2">
                        <ArrowLeft className="h-4 w-4" /> Ana Sayfaya Dön
                    </Button>
                </Link>
            </div>

            {/* Left Side: Visuals */}
            <div className="hidden lg:flex w-1/2 relative items-center justify-center bg-black/40 backdrop-blur-sm border-r border-white/5">
                <div className="absolute inset-0 bg-gradient-to-br from-primary/20 via-transparent to-purple-500/20 opacity-50" />
                <div className="relative z-10 p-12 text-center">
                    <img
                        src={dinoSkeleton}
                        alt="Dino Skeleton"
                        className="w-full max-w-lg mx-auto opacity-80 drop-shadow-[0_0_50px_rgba(255,255,255,0.2)] animate-pulse-slow"
                    />
                    <h2 className="mt-8 text-4xl font-black tracking-tighter bg-gradient-to-r from-primary to-emerald-400 bg-clip-text text-transparent">
                        Jurassic Çağında<br />Modern Bankacılık
                    </h2>
                    <p className="mt-4 text-slate-400 max-w-md mx-auto">
                        Hesabını oluştur, fosil yakıt hızında işlemlerin keyfini çıkar.
                        DinoBank ile paran güvende, geleceğin ellerinde.
                    </p>
                </div>
            </div>

            {/* Right Side: Form */}
            <div className="w-full lg:w-1/2 flex items-center justify-center p-8 overflow-y-auto">
                <div className="w-full max-w-md space-y-8">
                    <div className="text-center lg:text-left">
                        <h1 className="text-3xl font-bold">Aramıza Katıl 🦖</h1>
                        <p className="text-slate-400 mt-2">Finansal evrimini bugün başlat.</p>
                    </div>

                    <form onSubmit={handleRegister} className="space-y-4">
                        <div className="grid grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <Label htmlFor="ad" className="text-slate-300">Ad</Label>
                                <Input id="ad" placeholder="Ahmet" onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="soyad" className="text-slate-300">Soyad</Label>
                                <Input id="soyad" placeholder="Yılmaz" onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                            </div>
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="email" className="text-slate-300">E-posta</Label>
                            <Input id="email" type="email" placeholder="m@ornek.com" onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                        </div>

                        <div className="grid grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <Label htmlFor="tcKimlikNo" className="text-slate-300">TC Kimlik No</Label>
                                <Input id="tcKimlikNo" placeholder="11111111111" onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="telefon" className="text-slate-300">Telefon</Label>
                                <Input id="telefon" placeholder="555..." onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                            </div>
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="dogumTarihi" className="text-slate-300">Doğum Tarihi</Label>
                            <Input id="dogumTarihi" type="date" onChange={handleChange} required className="bg-white/5 border-white/10 text-white focus:border-primary/50" />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="sifre" className="text-slate-300">Şifre</Label>
                            <Input id="sifre" type="password" onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="adres" className="text-slate-300">Adres</Label>
                            <Input id="adres" placeholder="İstanbul..." onChange={handleChange} required className="bg-white/5 border-white/10 focus:border-primary/50" />
                        </div>

                        {error && <p className="text-sm text-red-400 bg-red-900/20 p-3 rounded border border-red-900/50">{error}</p>}

                        <Button type="submit" className="w-full bg-gradient-to-r from-primary to-emerald-500 hover:from-primary/90 hover:to-emerald-600 text-black font-bold h-11 shadow-lg shadow-primary/20" disabled={loading}>
                            {loading ? <Loader2 className="mr-2 h-4 w-4 animate-spin" /> : 'Hesap Oluştur'}
                        </Button>
                    </form>

                    <div className="text-center pt-4 border-t border-white/10">
                        <p className="text-sm text-slate-500">
                            Zaten bir hesabın var mı?{' '}
                            <Link to="/login" className="text-primary hover:text-emerald-400 font-bold transition-colors">
                                Giriş Yap
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}
