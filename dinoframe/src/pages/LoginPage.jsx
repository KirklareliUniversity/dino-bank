import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { login } from '@/lib/api';
import { Lock, Mail, Loader2, ArrowLeft } from 'lucide-react';

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true); // ... existing code

        try {
            const data = await login(email, password);
            // Store user info for Dashboard to use
            localStorage.setItem('dinoUser', JSON.stringify(data));
            navigate('/dashboard');
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleDemoLogin = async (email, password) => {
        setEmail(email);
        setPassword(password);
        setLoading(true);
        try {
            const data = await login(email, password);
            localStorage.setItem('dinoUser', JSON.stringify(data));
            navigate('/dashboard');
        } catch (err) {
            setError("Demo login failed: " + err.message);
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center p-4 relative overflow-hidden">
            {/* Background Elements */}
            <div className="absolute top-[-10%] left-[-10%] w-[500px] h-[500px] bg-primary/20 rounded-full blur-[100px]" />
            <div className="absolute bottom-[-10%] right-[-10%] w-[500px] h-[500px] bg-blue-500/10 rounded-full blur-[100px]" />

            {/* Back to Home Button */}
            <div className="absolute top-6 left-6 z-50">
                <Link to="/">
                    <Button variant="ghost" className="text-slate-400 hover:text-white hover:bg-white/10 gap-2">
                        <ArrowLeft className="h-4 w-4" /> Ana Sayfaya Dön
                    </Button>
                </Link>
            </div>

            <Card className="w-full max-w-md glass border-white/10 shadow-2xl relative z-10">
                <CardHeader className="space-y-2 text-center pb-8">
                    <div className="mx-auto w-16 h-16 bg-primary/20 rounded-2xl flex items-center justify-center mb-4 border border-primary/20">
                        <span className="text-3xl">🦖</span>
                    </div>
                    <CardTitle className="text-3xl font-bold tracking-tight bg-gradient-to-r from-white to-white/60 bg-clip-text text-transparent">
                        DinoBank
                    </CardTitle>
                    <CardDescription className="text-slate-400 text-base">
                        Tarih öncesi güven, modern hız.
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleLogin} className="space-y-5">
                        <div className="space-y-2">
                            <Label htmlFor="email" className="text-slate-300">E-posta</Label>
                            <div className="relative group">
                                <Mail className="absolute left-3 top-3 h-4 w-4 text-slate-500 group-focus-within:text-primary transition-colors" />
                                <Input
                                    id="email"
                                    type="email"
                                    placeholder="m@ornek.com"
                                    className="pl-10 bg-black/20 border-white/10 focus:border-primary/50 focus:ring-primary/20 h-11 transition-all"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="password" className="text-slate-300">Şifre</Label>
                            <div className="relative group">
                                <Lock className="absolute left-3 top-3 h-4 w-4 text-slate-500 group-focus-within:text-primary transition-colors" />
                                <Input
                                    id="password"
                                    type="password"
                                    className="pl-10 bg-black/20 border-white/10 focus:border-primary/50 focus:ring-primary/20 h-11 transition-all"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </div>
                        </div>
                        {error && <p className="text-sm text-red-400 font-medium bg-red-900/20 p-3 rounded-lg border border-red-900/50">{error}</p>}

                        <Button type="submit" className="w-full h-11 bg-primary hover:bg-primary/90 text-black font-bold text-base shadow-lg shadow-primary/20 transition-all hover:scale-[1.02]" disabled={loading}>
                            {loading ? <Loader2 className="mr-2 h-5 w-5 animate-spin" /> : 'Giriş Yap'}
                        </Button>

                        <div className="relative py-2">
                            <div className="absolute inset-0 flex items-center">
                                <span className="w-full border-t border-white/10" />
                            </div>
                            <div className="relative flex justify-center text-xs uppercase">
                                <span className="bg-transparent px-2 text-slate-500 font-medium">
                                    Hızlı Demo Girişi
                                </span>
                            </div>
                        </div>

                        <div className="grid grid-cols-3 gap-2">
                            <Button
                                type="button"
                                variant="outline"
                                className="h-10 border-white/10 bg-white/5 hover:bg-white/10 hover:text-white text-slate-300 text-xs"
                                onClick={() => handleDemoLogin('osman@dino.com', '123')}
                            >
                                🦖 Osman
                            </Button>
                            <Button
                                type="button"
                                variant="outline"
                                className="h-10 border-white/10 bg-white/5 hover:bg-white/10 hover:text-white text-slate-300 text-xs"
                                onClick={() => handleDemoLogin('kursat@dino.com', '123')}
                            >
                                🦕 Kürşat
                            </Button>
                            <Button
                                type="button"
                                variant="outline"
                                className="h-10 border-white/10 bg-white/5 hover:bg-white/10 hover:text-white text-slate-300 text-xs"
                                onClick={() => handleDemoLogin('ilkmert@dino.com', '123')}
                            >
                                🥚 İlkmert
                            </Button>
                        </div>
                    </form>
                </CardContent>
                <CardFooter className="flex justify-center pb-8">
                    <p className="text-sm text-slate-500">
                        Hesabınız yok mu?{' '}
                        <Link to="/register" className="text-primary hover:text-primary/80 font-semibold transition-colors">
                            Kayıt Ol
                        </Link>
                    </p>
                </CardFooter>
            </Card>
        </div>
    );
}
