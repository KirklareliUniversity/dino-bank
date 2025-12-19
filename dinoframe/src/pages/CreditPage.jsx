import { useState, useEffect } from 'react';
import Sidebar from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Banknote, CheckCircle, XCircle, AlertCircle, Clock } from 'lucide-react';

export default function CreditPage() {
    const [amount, setAmount] = useState('');
    const [installment, setInstallment] = useState('12');
    const [purpose, setPurpose] = useState('');
    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null); // { status: 'APPROVED' | 'REJECTED', message: '' }
    const [history, setHistory] = useState([]);

    const fetchHistory = async () => {
        try {
            const userStr = localStorage.getItem('dinoUser');
            if (!userStr) return;
            const user = JSON.parse(userStr);

            const response = await fetch(`http://localhost:8081/api/credits/history/${user.id}`);
            if (response.ok) {
                const data = await response.json();
                // Sort by date desc (newest first) helper
                const sorted = data.sort((a, b) => new Date(b.applicationDate) - new Date(a.applicationDate));
                setHistory(sorted);
            }
        } catch (err) {
            console.error("Failed to fetch history", err);
        }
    };

    useEffect(() => {
        fetchHistory();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setResult(null);

        const userStr = localStorage.getItem('dinoUser');
        if (!userStr) {
            setResult({ status: 'ERROR', message: 'Kullanıcı oturumu bulunamadı!' });
            setLoading(false);
            return;
        }
        const user = JSON.parse(userStr);

        try {
            // For demo purposes, we might need a customerId. 
            // In a real app, this comes from the auth token/context.
            // Using a hardcoded ID for now or assuming the backend handles it via context if we had one.
            // Since AuthController was seen using SecurityContext, we might need to send a token.
            // But looking at previous files, I didn't see a clear Auth Context provider usage in the frontend snippets I read.
            // However, the Sidebar has a "localStorage.removeItem('token')" logout logic.
            // So we should probably send the token in headers if we were doing a real fetch.
            // Given the task scope, I will fetch to the endpoint. 
            // Wait, the CreditService expects a custom object with customerId. 
            // I'll assume for this prototype we explicitly send customerId: 1 (as established in previous turns usually).
            // Or I retrieve it from localStorage if stored.

            // Let's use a dummy customer ID '1' for the demo as seen in typical dev scenarios or try to parse token.
            // For safety/simplicity in this "fix" task, I'll send customerId: 1.

            const response = await fetch('http://localhost:8081/api/credits/apply', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // 'Authorization': `Bearer ${localStorage.getItem('token')}` // If we needed auth
                },
                body: JSON.stringify({
                    customerId: user.id, // Hardcoded for demo simplicity as user management wasn't the focus
                    requestedAmount: parseFloat(amount),
                    installmentCount: parseInt(installment),
                    purpose: purpose
                })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Başvuru sırasında bir hata oluştu');
            }

            if (data.status === 'APPROVED') {
                setResult({
                    status: 'APPROVED',
                    message: `Tebrikler! ${data.requestedAmount} TL tutarındaki krediniz onaylandı ve hesabınıza yatırıldı.`
                });
            } else if (data.status === 'REJECTED') {
                setResult({
                    status: 'REJECTED',
                    message: `Üzgünüz, başvurunuz reddedildi. Sebep: ${data.rejectionReason}`
                });
            } else {
                setResult({
                    status: 'PENDING',
                    message: 'Başvurunuz alındı, değerlendirme sürecinde.'
                });
            }

            // Refresh history after new application
            fetchHistory();

        } catch (error) {
            console.error('Error applying for credit:', error);
            setResult({
                status: 'ERROR',
                message: error.message || 'Bir hata oluştu. Lütfen tekrar deneyiniz.'
            });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex min-h-screen bg-background text-foreground">
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="mb-10">
                    <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-green-400 to-emerald-600 bg-clip-text text-transparent">
                        Kredi Başvurusu
                    </h1>
                    <p className="text-slate-400 mt-1">
                        Hayallerinize bir adım daha yaklaşın. Anında kredi sonucu.
                    </p>
                </header>

                <div className="max-w-2xl mx-auto">
                    <Card className="bg-black/40 border-white/10 backdrop-blur-sm">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-2 text-slate-200">
                                <Banknote className="h-6 w-6 text-emerald-500" />
                                Hızlı Kredi
                            </CardTitle>
                            <CardDescription>
                                Bakiyenize dayalı anında onaylanan kredi sistemi.
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <form onSubmit={handleSubmit} className="space-y-6">
                                <div className="space-y-2">
                                    <Label htmlFor="amount">Kredi Tutarı (TL)</Label>
                                    <Input
                                        id="amount"
                                        type="number"
                                        placeholder="Örn: 20000"
                                        value={amount}
                                        onChange={(e) => setAmount(e.target.value)}
                                        required
                                        className="bg-black/20 border-white/10 text-white"
                                    />
                                </div>

                                <div className="space-y-2">
                                    <Label htmlFor="installment">Vade (Ay)</Label>
                                    <select
                                        id="installment"
                                        value={installment}
                                        onChange={(e) => setInstallment(e.target.value)}
                                        className="flex h-10 w-full rounded-md border border-white/10 bg-black/20 px-3 py-2 text-sm ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 text-white"
                                    >
                                        <option value="12">12 Ay</option>
                                        <option value="24">24 Ay</option>
                                        <option value="36">36 Ay</option>
                                        <option value="48">48 Ay</option>
                                    </select>
                                </div>

                                <div className="space-y-2">
                                    <Label htmlFor="purpose">Kullanım Amacı</Label>
                                    <Input
                                        id="purpose"
                                        placeholder="Örn: Tatil, Eğitim, Tadilat"
                                        value={purpose}
                                        onChange={(e) => setPurpose(e.target.value)}
                                        required
                                        className="bg-black/20 border-white/10 text-white"
                                    />
                                </div>

                                <Button
                                    type="submit"
                                    className="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold"
                                    disabled={loading}
                                >
                                    {loading ? 'Değerlendiriliyor...' : 'Başvuruyu Tamamla'}
                                </Button>
                            </form>

                            {result && (
                                <div className={`mt-6 p-4 rounded-lg flex items-start gap-3 border ${result.status === 'APPROVED' ? 'bg-emerald-500/10 border-emerald-500/30 text-emerald-400' :
                                    result.status === 'REJECTED' ? 'bg-red-500/10 border-red-500/30 text-red-400' :
                                        'bg-blue-500/10 border-blue-500/30 text-blue-400'
                                    }`}>
                                    {result.status === 'APPROVED' && <CheckCircle className="h-6 w-6 shrink-0" />}
                                    {result.status === 'REJECTED' && <XCircle className="h-6 w-6 shrink-0" />}
                                    {result.status === 'ERROR' && <AlertCircle className="h-6 w-6 shrink-0" />}

                                    <div>
                                        <h4 className="font-bold">
                                            {result.status === 'APPROVED' ? 'Onaylandı!' :
                                                result.status === 'REJECTED' ? 'Reddedildi' : 'İşlem Sonucu'}
                                        </h4>
                                        <p className="text-sm mt-1 opacity-90">{result.message}</p>
                                    </div>
                                </div>
                            )}
                        </CardContent>
                    </Card>

                    {/* History Section */}
                    <div className="mt-8">
                        <h2 className="text-2xl font-bold mb-4 text-slate-200">Geçmiş Başvurularım</h2>
                        <Card className="bg-black/20 border-white/5">
                            <CardContent className="p-0">
                                <Table>
                                    <TableHeader>
                                        <TableRow className="border-white/5 hover:bg-transparent">
                                            <TableHead className="text-slate-400">Tarih</TableHead>
                                            <TableHead className="text-slate-400">Amaç</TableHead>
                                            <TableHead className="text-slate-400">Tutar</TableHead>
                                            <TableHead className="text-slate-400">Durum</TableHead>
                                            <TableHead className="text-slate-400">Açıklama</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        {history.length === 0 ? (
                                            <TableRow>
                                                <TableCell colSpan={5} className="text-center py-8 text-slate-500">
                                                    Henüz kredi başvurunuz bulunmuyor.
                                                </TableCell>
                                            </TableRow>
                                        ) : (
                                            history.map((item) => (
                                                <TableRow key={item.id} className="border-white/5 hover:bg-white/5">
                                                    <TableCell className="font-medium text-slate-300">
                                                        {new Date(item.applicationDate).toLocaleDateString('tr-TR')}
                                                    </TableCell>
                                                    <TableCell className="text-slate-300">{item.purpose}</TableCell>
                                                    <TableCell className="text-slate-300">₺{item.requestedAmount.toLocaleString('tr-TR')}</TableCell>
                                                    <TableCell>
                                                        <span className={`px-2 py-1 rounded-full text-xs font-bold ${item.status === 'APPROVED' ? 'bg-emerald-500/20 text-emerald-400' :
                                                            item.status === 'REJECTED' ? 'bg-red-500/20 text-red-400' :
                                                                'bg-yellow-500/20 text-yellow-400'
                                                            }`}>
                                                            {item.status === 'APPROVED' ? 'ONAYLANDI' :
                                                                item.status === 'REJECTED' ? 'REDDEDİLDİ' : 'BEKLEMEDE'}
                                                        </span>
                                                    </TableCell>
                                                    <TableCell className="text-slate-400 text-sm max-w-[200px] truncate" title={item.rejectionReason}>
                                                        {item.rejectionReason || '-'}
                                                    </TableCell>
                                                </TableRow>
                                            ))
                                        )}
                                    </TableBody>
                                </Table>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>
        </div>
    );
}
