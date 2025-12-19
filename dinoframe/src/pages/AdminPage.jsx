import { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { request } from '@/lib/api';

export default function AdminPage() {
    const [data, setData] = useState({ customers: [], accounts: [] });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await request('/admin/db');
                setData(result);
            } catch (error) {
                console.error("Admin fetch error:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    if (loading) return <div className="text-white text-center p-10">Veritabanı Yükleniyor...</div>;

    return (
        <div className="min-h-screen bg-slate-950 p-8 text-white">
            <h1 className="text-3xl font-bold mb-8 text-primary">Admin Veritabanı Görünümü</h1>

            <div className="grid gap-8">
                {/* Customers Table */}
                <Card className="bg-slate-900 border-slate-800">
                    <CardHeader>
                        <CardTitle className="text-xl text-slate-200">Müşteriler (Customers)</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="overflow-x-auto">
                            <Table>
                                <TableHeader>
                                    <TableRow className="border-slate-800">
                                        <TableHead className="text-slate-400">ID</TableHead>
                                        <TableHead className="text-slate-400">Ad Soyad</TableHead>
                                        <TableHead className="text-slate-400">Email</TableHead>
                                        <TableHead className="text-slate-400">TCKN</TableHead>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    {data.customers?.map(c => (
                                        <TableRow key={c.id} className="border-slate-800">
                                            <TableCell>{c.id}</TableCell>
                                            <TableCell>{c.ad} {c.soyad}</TableCell>
                                            <TableCell>{c.email}</TableCell>
                                            <TableCell>{c.tcKimlikNo}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </div>
                    </CardContent>
                </Card>

                {/* Accounts Table */}
                <Card className="bg-slate-900 border-slate-800">
                    <CardHeader>
                        <CardTitle className="text-xl text-slate-200">Hesaplar (Accounts)</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="overflow-x-auto">
                            <Table>
                                <TableHeader>
                                    <TableRow className="border-slate-800">
                                        <TableHead className="text-slate-400">ID</TableHead>
                                        <TableHead className="text-slate-400">Hesap No</TableHead>
                                        <TableHead className="text-slate-400">Müşteri ID</TableHead>
                                        <TableHead className="text-slate-400 text-right">Bakiye</TableHead>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    {data.accounts?.map(a => (
                                        <TableRow key={a.id} className="border-slate-800">
                                            <TableCell>{a.id}</TableCell>
                                            <TableCell>{a.accountNumber}</TableCell>
                                            {/* Check if customer object or ID is returned */}
                                            <TableCell>{a.customer?.id || a.customerId}</TableCell>
                                            <TableCell className="text-right">₺{a.balance}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </div>
                    </CardContent>
                </Card>

                {/* Transactions Table */}
                <Card className="bg-slate-900 border-slate-800">
                    <CardHeader>
                        <CardTitle className="text-xl text-slate-200">İşlemler (Transactions)</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="overflow-x-auto">
                            <Table>
                                <TableHeader>
                                    <TableRow className="border-slate-800">
                                        <TableHead className="text-slate-400">ID</TableHead>
                                        <TableHead className="text-slate-400">Tip</TableHead>
                                        <TableHead className="text-slate-400">Tutar</TableHead>
                                        <TableHead className="text-slate-400">Açıklama</TableHead>
                                        <TableHead className="text-slate-400">Tarih</TableHead>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    {data.transactions?.map(t => (
                                        <TableRow key={t.id} className="border-slate-800">
                                            <TableCell>{t.id}</TableCell>
                                            <TableCell>{t.transactionType}</TableCell>
                                            <TableCell>₺{t.amount}</TableCell>
                                            <TableCell>{t.description}</TableCell>
                                            <TableCell>{t.transactionDate}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
