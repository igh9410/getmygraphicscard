function SubscriptionPage() {
  const fetchSubscriptons = async () => {
    const baseUrl: string = "http://localhost:8888/api/subscriptions/2";

    const response = await fetch(baseUrl);
    const responseJson = await response.json();
    if (!response.ok) {
      throw new Error("Something went wrong!");
    }
  };

  return <div>SubscriptionPage</div>;
}

export default SubscriptionPage;
